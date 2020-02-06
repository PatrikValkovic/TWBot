package cz.valkovic.twbot.modules.core.events.instances;

import cz.valkovic.twbot.modules.core.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.Logger;

/**
 * Event that is called when the application is closing.
 */
@AllArgsConstructor
public class ApplicationCloseEvent implements Event {

    /**
     * Closing logger.
     */
    @Getter
    private final Logger log;
}
