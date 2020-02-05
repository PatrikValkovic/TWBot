package cz.valkovic.twbot.services.messaging.messages;

import cz.valkovic.twbot.modules.core.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WebLoaded implements Event {

    @Getter
    String content;

    @Getter
    String location;

}
