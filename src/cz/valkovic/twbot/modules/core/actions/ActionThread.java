package cz.valkovic.twbot.modules.core.actions;

import cz.valkovic.twbot.modules.core.execution.ExecutionService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.observable.Observable;
import cz.valkovic.twbot.modules.core.observable.ObservableFactory;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.twbot.modules.core.settings.instances.CorePublicSetting;
import javafx.scene.web.WebEngine;
import lombok.Getter;
import javax.inject.Inject;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class ActionThread implements Runnable {

    @Getter
    private final Observable<Boolean> hasActionsToPerform;
    @Getter
    private final AtomicBoolean exit = new AtomicBoolean(false);

    private final BlockingQueue<BiConsumer<WebEngine, Runnable>> queue = new LinkedBlockingQueue<>();
    private final AtomicBoolean actionWaitLock = new AtomicBoolean(false);

    private CorePublicSetting pubSetting;
    @SuppressWarnings("FieldCanBeLocal")
    private Object pubSettingLock;
    private CorePrivateSetting privSetting;

    private final LoggingService log;
    private final ExecutionService exe;
    private final WebEngineProvider engineProvider;

    @Inject
    public ActionThread(
            ObservableFactory observFact,
            SettingsProviderService setting,
            LoggingService log, ExecutionService exe, WebEngineProvider engineProvider) {
        this.hasActionsToPerform = observFact.create(false);
        this.log = log;
        this.exe = exe;
        this.engineProvider = engineProvider;

        setting.observe(CorePrivateSetting.class, s -> this.privSetting = s);
        pubSettingLock = setting.observe(CorePublicSetting.class, s -> this.pubSetting = s);
    }

    public void addAction(BiConsumer<WebEngine, Runnable> action){
        this.queue.add(action);
    }

    private void notifyLock() {
        synchronized (this.actionWaitLock){
            this.actionWaitLock.set(true);
            actionWaitLock.notifyAll();
        }
    }

    @Override
    public void run() {
        this.log.getAction().info("Starting action thread");
        while(this.engineProvider.getEngine() == null || this.privSetting == null){
            Thread.yield();
        }
        this.log.getAction().info("Engine provider in action thread loaded");
        Random rand = new Random();
        try {
            while (!this.exit.get() || !this.queue.isEmpty()) {
                this.hasActionsToPerform.setValue(!this.queue.isEmpty());
                var toExecute = this.queue.poll(this.privSetting.maxLockWaitingTime() / 2, TimeUnit.MILLISECONDS);
                if(toExecute == null) {
                    this.log.getAction().debug("Action thread has no task to execute, looping");
                    continue;
                }
                // perform action
                this.hasActionsToPerform.setValue(true);
                this.log.getAction().debug("Executing action " + toExecute.getClass().getCanonicalName());
                try {
                    synchronized (this.actionWaitLock) {
                        this.actionWaitLock.set(false);
                        this.exe.runInRender(() -> toExecute.accept(this.engineProvider.getEngine(), this::notifyLock));
                        this.actionWaitLock.wait(privSetting.maxLockWaitingTime() / 2);
                    }
                }
                catch(Exception e) {
                    this.log.getAction().warn("Exception during action");
                    this.log.getAction().debug(e, e);
                }
                // timeouted
                if(!actionWaitLock.get()) {
                    this.log.getAction().warn("Action timeout " + toExecute.getClass().getCanonicalName());
                    continue;
                }
                // waiting before next action
                int minWait = pubSetting.actionTimeMin();
                int maxWait = pubSetting.actionTimeMax();
                int toWait = rand.nextInt(maxWait - minWait) + minWait;
                this.log.getAction().debug("Action thread will wait " + toWait + "ms");
                Thread.sleep(toWait);
            }
        }
        catch (InterruptedException e) {
            this.log.getAction().error("Action thread interrupted");
            this.log.getAction().debug(e, e);
        }
        finally {
            this.log.getAction().info("Closing action thread");
        }
    }
}
