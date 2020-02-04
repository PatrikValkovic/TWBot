package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.modules.core.events.Event;
import javafx.scene.web.WebEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@AllArgsConstructor
public class PerformNoWaitAction implements Event {
    @Getter
    Consumer<WebEngine> action;
}
