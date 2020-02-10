package cz.valkovic.twbot.modules.browsing.actions;

import cz.valkovic.twbot.modules.browsing.events.PageLoadedEvent;
import cz.valkovic.twbot.modules.core.actions.ActionsService;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import javafx.scene.web.WebEngine;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ActionsWithReloadServiceImpl implements ActionsWithReloadService {

    private final ActionsService action;
    private final EventBrokerService event;
    private final LoggingService log;

    @Inject
    public ActionsWithReloadServiceImpl(ActionsService action, EventBrokerService event, LoggingService log) {
        this.action = action;
        this.event = event;
        this.log = log;
    }

    @Override
    public void action(Consumer<WebEngine> action) {
        String actionName = action.getClass().getCanonicalName();
        this.log.getAction().debug("Adding action with wait for page reload " + actionName);
        this.action.action((engine, complete) -> {
            this.log.getAction().debug("Executing action with wait got page reload " + actionName);
            AtomicReference<Object> eventHandler = new AtomicReference<>(null);
            eventHandler.set(event.listenTo(PageLoadedEvent.class, e -> {
                event.remove(eventHandler.get());
                complete.run();
            }));
            action.accept(engine);
        });
    }
}
