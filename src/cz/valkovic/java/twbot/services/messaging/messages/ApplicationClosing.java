package cz.valkovic.java.twbot.services.messaging.messages;

import cz.valkovic.java.twbot.services.messaging.Message;
import lombok.Getter;
import org.apache.logging.log4j.Logger;

public class ApplicationClosing implements Message {

    @Getter
    private Logger log;

    public ApplicationClosing(Logger log) {
        this.log = log;
    }
}
