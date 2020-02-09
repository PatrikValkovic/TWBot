package cz.valkovic.twbot.modules.core.actions;

import cz.valkovic.twbot.modules.core.actions.events.ActionRequestEvent;
import cz.valkovic.twbot.modules.core.actions.events.ExplicitActionRequestEvent;
import cz.valkovic.twbot.modules.core.actions.events.NowaitActionRequestEvent;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import javax.inject.Inject;

/**
 * Register for action events and invoke them on {@link ActionsService}.
 */
public class ActionEventHandler {

    @Inject
    public static void register(EventBrokerService event, ActionsService actions, LoggingService log) {
        log.getAction().debug("Registering events for actions");
        event.listenTo(ActionRequestEvent.class, a -> actions.action(a.getAction()));
        event.listenTo(ExplicitActionRequestEvent.class, a -> actions.action(a.getAction()));
        event.listenTo(NowaitActionRequestEvent.class, a -> actions.actionWithoutWait(a.getAction()));
    }


}
