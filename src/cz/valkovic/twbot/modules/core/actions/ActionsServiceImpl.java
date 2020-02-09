package cz.valkovic.twbot.modules.core.actions;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.observable.Observable;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import javafx.scene.web.WebEngine;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Singleton
public class ActionsServiceImpl implements ActionsService {

    private final LoggingService log;

    private final Thread thread;
    private final ActionThread actionThread;
    private CorePrivateSetting setting;

    @Inject
    public ActionsServiceImpl(
            LoggingService log,
            Provider<ActionThread> actionThreadProvider,
            SettingsProviderService settings
    ) {
        this.log = log;
        this.actionThread = actionThreadProvider.get();
        this.thread = new Thread(this.actionThread, "ActionThread");
        this.thread.start();
        settings.observe(CorePrivateSetting.class, s -> this.setting = s);
    }

    @Override
    public void action(BiConsumer<WebEngine, Runnable> action) {
        this.actionThread.addAction(action);
    }

    @Override
    public void action(Consumer<WebEngine> action) {
        this.action((engine, complete) -> {
            this.log.getAction().debug("Action with implicit wait " + action.getClass().getCanonicalName());
            action.accept(engine);
            complete.run();
        });
    }

    @Override
    public void actionWithoutWait(Consumer<WebEngine> action) {
        this.action((engine, complete) -> {
            this.log.getAction().debug("Action without wait " + action.getClass().getCanonicalName());
            complete.run();
            action.accept(engine);
        });
    }

    @Override
    public void stopAndJoin() {
        if(!this.thread.isAlive())
            return;

        this.log.getAction().debug("Stopping action thread");
        this.actionThread.getExit().set(true);

        try {
            this.thread.join(this.setting.maxLockWaitingTime());
            if(this.thread.isAlive()){
                this.log.getAction().error("Action thread couldn't be joined, interrupting");
                this.thread.interrupt();
            }
        }
        catch(InterruptedException e) {
            this.log.getAction().warn("Interrupted action thread stopping");
            this.log.getAction().debug(e, e);
        }
    }

    @Override
    public Observable<Boolean> hasActions() {
        return this.actionThread.getHasActionsToPerform();
    }
}
