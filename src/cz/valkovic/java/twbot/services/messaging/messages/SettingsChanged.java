package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.services.configuration.PublicConfiguration;
import cz.valkovic.java.twbot.services.messaging.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SettingsChanged implements Message {

    @Getter
    private PublicConfiguration conf;

}
