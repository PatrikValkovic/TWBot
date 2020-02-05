package cz.valkovic.twbot.services.messaging.messages;

import cz.valkovic.twbot.modules.core.events.Event;
import cz.valkovic.twbot.services.configuration.PublicConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SettingsChanged implements Event {

    @Getter
    private PublicConfiguration conf;

}
