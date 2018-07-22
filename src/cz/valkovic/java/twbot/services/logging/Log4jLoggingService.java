package cz.valkovic.java.twbot.services.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jLoggingService implements LoggingService {

    @Override
    public Logger getStartup() {
        return LogManager.getFormatterLogger("Startup");
    }
}
