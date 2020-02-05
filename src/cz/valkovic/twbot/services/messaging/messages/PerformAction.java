package cz.valkovic.twbot.services.messaging.messages;

import cz.valkovic.twbot.modules.core.events.Event;
import java.util.function.Function;
import javafx.scene.web.WebEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PerformAction implements Event {
    @Getter
    Function<WebEngine, Boolean> action;
}
