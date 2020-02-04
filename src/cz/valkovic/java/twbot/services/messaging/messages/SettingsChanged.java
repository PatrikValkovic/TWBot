package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.services.configuration.PublicConfiguration;
import cz.valkovic.java.twbot.modules.core.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SettingsChanged implements Event {

    @Getter
    private PublicConfiguration conf;

}
