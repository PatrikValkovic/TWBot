package cz.valkovic.twbot.modules.parsing.handle;

import cz.valkovic.twbot.modules.browsing.events.PageLoadedEvent;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import javax.inject.Inject;

public class EventHandler {

    @Inject
    public static void register(EventBrokerService eventService, ParsingRequestService parse) {
        eventService.listenTo(PageLoadedEvent.class, event -> parse.parse());
        eventService.listenTo(ParseRequestEvent.class, event -> parse.parse());
    }

}
