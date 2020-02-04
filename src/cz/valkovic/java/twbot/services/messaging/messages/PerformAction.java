package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.modules.core.events.Event;
import javafx.scene.web.WebEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@AllArgsConstructor
public class PerformAction implements Event {
    @Getter
    Function<WebEngine, Boolean> action;
}
