package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.services.messaging.Message;
import javafx.scene.web.WebEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@AllArgsConstructor
public class PerformWaitAction implements Message {
    @Getter
    Consumer<WebEngine> action;
}
