package cz.valkovic.twbot.modules.browsing.events;

import cz.valkovic.twbot.modules.core.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event that occurs when the page is loaded.
 */
@AllArgsConstructor
public class PageLoadedEvent implements Event {

    /**
     * Location of the page.
     */
    @Getter
    private String location;

}
