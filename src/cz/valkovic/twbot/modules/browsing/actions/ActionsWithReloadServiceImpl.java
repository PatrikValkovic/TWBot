package cz.valkovic.twbot.modules.browsing.actions;

import cz.valkovic.twbot.modules.browsing.events.PageLoadedEvent;
import cz.valkovic.twbot.modules.core.actions.ActionsService;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import javafx.scene.web.WebEngine;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ActionsWithReloadServiceImpl implements ActionsWithReloadService {

    private final ActionsService action;
    private final EventBrokerService event;

    @Inject
    public ActionsWithReloadServiceImpl(ActionsService action, EventBrokerService event) {
        this.action = action;
        this.event = event;
    }

    @Override
    public void action(Consumer<WebEngine> action) {
        this.action.action((engine, complete) -> {
            AtomicReference<Object> eventHandler = new AtomicReference<>(null);
            eventHandler.set(event.listenTo(PageLoadedEvent.class, e -> {
                event.remove(eventHandler.get());
                complete.run();
            }));
            action.accept(engine);
        });
    }
}
