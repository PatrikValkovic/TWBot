package cz.valkovic.twbot.services.browserManipulation;

import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.events.instances.ApplicationCloseEvent;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.twbot.modules.core.settings.instances.CorePublicSetting;
import cz.valkovic.twbot.services.messaging.messages.PerformAction;
import cz.valkovic.twbot.services.messaging.messages.PerformNoWaitAction;
import cz.valkovic.twbot.services.messaging.messages.PerformWaitAction;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import lombok.Getter;
import lombok.Setter;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

@Singleton
public class ActionServiceImpl implements ActionsService {

    private LoggingService log;

    private ActionsThread actionsThread;
    private final Object waitingLock = new Object();

    @Inject
    public ActionServiceImpl(SettingsProviderService setting,
                             LoggingService log,
                             EventBrokerService message,
                             Actionable actionable) {
        this.log = log;

        this.actionsThread = new ActionsThread(
                actionable,
                setting,
                log
        );
        this.actionsThread.start();

        AtomicInteger lockWaitingTime = new AtomicInteger();
        setting.observe(CorePrivateSetting.class, s -> lockWaitingTime.set(s.maxLockWaitingTime()));

        message.listenTo(PerformAction.class, e -> this.performAction(e.getAction()));
        message.listenTo(PerformNoWaitAction.class, e -> this.performNoWaitAction(e.getAction()));
        message.listenTo(PerformWaitAction.class, e -> this.performWaitAction(e.getAction()));
        message.listenTo(ApplicationCloseEvent.class, e -> {
           this.actionsThread.processing.set(false);
           synchronized(this.actionsThread.sleepLock){
               this.actionsThread.sleepLock.notify();
           }
           this.actionsThread.join(lockWaitingTime.get());
           if(!this.actionsThread.isAlive()){
               log.getAction().info("Action thread joined");
           }
           else {
               log.getAction().warn("Couldn't join action thread");
               this.actionsThread.interrupt();
           }
        });
    }

    @Inject
    public static void injectInstance(ActionServiceImpl instance, LoggingService log) {
        log.getLoading().debug("Action service loaded");  //TODO what?
    }

    //region actions threading
    private static class ActionsThread extends Thread
    {
        private static class ActionHelper implements Runnable {
            @Getter
            private boolean result;
            private Function<WebEngine, Boolean> method;
            @Setter
            private WebEngine engine;

            private ActionHelper(Function<WebEngine, Boolean> method) {
                this.method = method;
            }

            @Override
            public void run() {
                result = method.apply(engine);
                synchronized (this) {
                    this.notify();
                }
            }
        }

        @Getter
        private final BlockingQueue<ActionHelper> queue = new LinkedBlockingDeque<>();
        @Getter
        private final Object sleepLock = new Object();
        @Getter
        private final AtomicBoolean processing = new AtomicBoolean(true);

        private Actionable actionable;
        private LoggingService log;
        private CorePublicSetting pubSetting;
        @SuppressWarnings("FieldCanBeLocal")
        private Object corePublicSettingLock;
        private CorePrivateSetting privSetting;

        public ActionsThread(Actionable actionable,
                             SettingsProviderService settingProvider,
                             LoggingService log) {
            this.actionable = actionable;
            this.log = log;
            this.corePublicSettingLock = settingProvider.observe(
                    CorePublicSetting.class,
                    s -> this.pubSetting = s
            );
            settingProvider.observe(
                    CorePrivateSetting.class,
                    s -> this.privSetting = s
            );

            this.setName("Actions thread");
        }

        @Override
        public void run() {
            Random rand = new Random();
            while (this.processing.get()) {
                int difference = Math.abs(pubSetting.actionTimeMax() - pubSetting.actionTimeMin());
                int toWait = rand.nextInt(difference) + pubSetting.actionTimeMin();

                try {
                    log.getAction().debug("Thread will sleep for " + toWait + " milliseconds");
                    synchronized (this.sleepLock) {
                        this.sleepLock.wait(toWait);
                    }

                    if (!queue.isEmpty()) {
                        Object monitor = actionable.getActionMonitor();
                        synchronized (monitor) {
                            ActionHelper h = queue.poll();
                            WebEngine engine = actionable.getEngine();
                            if(engine == null){
                                this.queue.add(h);
                                continue;
                            }
                            h.setEngine(engine);
                            log.getAction().info("Action will be performed");
                            synchronized (h) {
                                Platform.runLater(h);
                                h.wait(privSetting.maxLockWaitingTime() / 4);
                            }
                            boolean result = h.isResult();
                            if (result) {
                                log.getAction().debug("Waiting for page load because of action");
                                monitor.wait(privSetting.maxLockWaitingTime() / 2);
                                log.getAction().debug("Page loaded because of action");
                            } else
                                log.getAction().debug("The proccess wil not wait for action to reload the page");
                        }
                    }
                }
                catch (InterruptedException e) {
                    log.getAction().info("Action thread interrupted");
                    log.getAction().debug(e, e);
                }
            }
            log.getAction().info("Actions thread exited");
        }
    }
    //endregion

    //region ActionService interface
    @Override
    public void performWaitAction(Consumer<WebEngine> callback) {
        this.performAction(webEngine -> {
            callback.accept(webEngine);
            return true;
        });
    }

    @Override
    public void performNoWaitAction(Consumer<WebEngine> callback) {
        this.performAction(webEngine -> {
            callback.accept(webEngine);
            return false;
        });
    }

    @Override
    public void performAction(Function<WebEngine, Boolean> callback) {
        this.log.getAction().debug("Adding action to the queue " + callback.getClass().getSimpleName());
        this.actionsThread.queue.add(new ActionsThread.ActionHelper(callback));
    }
    //endregion
}
