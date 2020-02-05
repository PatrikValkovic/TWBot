package cz.valkovic.twbot.modules.core.events.instances;

import cz.valkovic.twbot.modules.core.events.Event;
import lombok.Getter;
import org.apache.logging.log4j.Logger;

public class ApplicationCloseEvent implements Event {

    @Getter
    private Logger log;

    public ApplicationCloseEvent(Logger log) {
        this.log = log;
    }
}
