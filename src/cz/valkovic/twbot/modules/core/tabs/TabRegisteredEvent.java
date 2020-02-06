package cz.valkovic.twbot.modules.core.tabs;

import cz.valkovic.twbot.modules.core.events.Event;
import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event that is invoked when the new tab is registered into the system.
 */
@AllArgsConstructor
public class TabRegisteredEvent implements Event {
    /**
     * Name of the tab.
     */
    @Getter
    String name;

    /**
     * Class that represents content of the tab.
     */
    @Getter
    Class<? extends Node> contentClass;
}
