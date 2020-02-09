package cz.valkovic.twbot.modules.core.actions.events;

import cz.valkovic.twbot.modules.core.events.Event;
import javafx.scene.web.WebEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.function.BiConsumer;

/**
 * Event that invoke action with explicit waiting.
 */
@AllArgsConstructor
public class ExplicitActionRequestEvent implements Event {

    /**
     * Action to perform.
     */
    @Getter
    BiConsumer<WebEngine, Runnable> action;
}
