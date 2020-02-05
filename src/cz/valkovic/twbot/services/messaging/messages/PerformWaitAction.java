package cz.valkovic.twbot.services.messaging.messages;

import cz.valkovic.twbot.modules.core.events.Event;
import java.util.function.Consumer;
import javafx.scene.web.WebEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PerformWaitAction implements Event {
    @Getter
    Consumer<WebEngine> action;
}
