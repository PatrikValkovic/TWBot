package cz.valkovic.twbot.modules.browsing.events;

import cz.valkovic.twbot.modules.core.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event that occurs when the location is changed.
 */
@AllArgsConstructor
public class LocationChangedEvent implements Event {

    /**
     * Previous location.
     */
    @Getter
    String oldLocation;

    /**
     * Current location.
     */
    @Getter
    String newLocation;

}
