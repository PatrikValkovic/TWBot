package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.modules.core.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WebLoaded implements Event {

    @Getter
    String content;

    @Getter
    String location;

}
