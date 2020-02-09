package cz.valkovic.twbot.modules.core.actions.events;

import cz.valkovic.twbot.modules.core.events.Event;
import javafx.scene.web.WebEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.function.Consumer;

/**
 * Event that invoke action with implicit waiting.
 */
@AllArgsConstructor
public class ActionRequestEvent implements Event {

    /**
     * Action to perform.
     */
    @Getter
    Consumer<WebEngine> action;
}
