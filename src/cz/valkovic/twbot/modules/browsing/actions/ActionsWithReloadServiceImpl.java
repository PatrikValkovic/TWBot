package cz.valkovic.twbot.modules.browsing.actions;

import cz.valkovic.twbot.modules.browsing.events.PageLoadedEvent;
import cz.valkovic.twbot.modules.core.actions.ActionsService;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.twbot.modules.core.timing.TimingService;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import javafx.scene.web.WebEngine;
import javax.inject.Inject;

public class ActionsWithReloadServiceImpl implements ActionsWithReloadService {

    private final ActionsService action;
    private final EventBrokerService event;
    private final LoggingService log;
    private final TimingService timing;
    private CorePrivateSetting setting;

    @Inject
    public ActionsWithReloadServiceImpl(
            ActionsService action,
            EventBrokerService event,
            LoggingService log,
            SettingsProviderService setting,
            TimingService timing) {
        this.action = action;
        this.event = event;
        this.log = log;
        this.timing = timing;
        setting.observe(CorePrivateSetting.class, s -> this.setting = s);
    }

    @Override
    public void action(Consumer<WebEngine> action) {
        String actionName = action.getClass().getCanonicalName();
        this.log.getAction().debug("Adding action with wait for page reload " + actionName);
        this.action.action((engine, complete) -> {
            this.log.getAction().debug("Executing action with wait got page reload " + actionName);
            AtomicReference<Object> eventHandler = new AtomicReference<>(null);
            eventHandler.set(event.listenTo(PageLoadedEvent.class, e -> {
                synchronized (eventHandler) {
                    if(eventHandler.get() == null)
                        return;
                    event.remove(eventHandler.get());
                    eventHandler.set(null);
                    complete.run();
                }
            }));
            this.timing.executeAt(() -> {
                synchronized (eventHandler){
                    if(eventHandler.get() == null)
                        return;
                    event.remove(eventHandler.get());
                    eventHandler.set(null);
                    complete.run();
                }
                this.log.getAction().warn("Action with reload completed because of timeout");
            }, Instant.now().plus(Duration.ofMillis(setting.maxLockWaitingTime() / 2)));
            action.accept(engine);
        });
    }
}
