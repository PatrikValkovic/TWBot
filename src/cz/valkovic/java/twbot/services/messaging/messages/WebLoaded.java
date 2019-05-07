package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.services.messaging.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WebLoaded implements Message {

    @Getter
    String content;

    @Getter
    String location;

}
