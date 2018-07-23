package cz.valkovic.java.twbot.services.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jLoggingService implements LoggingService {

    @Override
    public Logger getStartup() {
        return LogManager.getFormatterLogger("Startup");
    }

    @Override
    public Logger getLoading() {
        return LogManager.getFormatterLogger("Loading");
    }

    @Override
    public Logger getLoadingResources() {
        return LogManager.getFormatterLogger("Loading.Resources");
    }

    @Override
    public Logger getExit() {
        return LogManager.getFormatterLogger("Exit");
    }
}
