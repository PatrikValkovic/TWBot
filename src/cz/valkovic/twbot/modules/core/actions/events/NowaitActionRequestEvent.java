package cz.valkovic.twbot.modules.core.actions.events;

import cz.valkovic.twbot.modules.core.events.Event;
import javafx.scene.web.WebEngine;
import lombok.Getter;
import java.util.function.Consumer;

/**
 * Event that invoke action without waiting.
 */
public class NowaitActionRequestEvent implements Event {

    /**
     * Action to perform.
     */
    @Getter
    Consumer<WebEngine> action;
}
