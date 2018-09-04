package cz.valkovic.java.twbot.services.logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;

@Singleton
public class Log4jLoggingService implements LoggingService {

    private static final String BASE_PACKAGE = "cz.valkovic.java.twbot";

    @Override
    public Logger getStartup() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".startup");
    }

    @Override
    public Logger getLoading() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".loading");
    }

    @Override
    public Logger getLoadingResources() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".loading.resources");
    }

    @Override
    public Logger getExit() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".exit");
    }

    @Override
    public Logger getParsing() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".parsing");
    }

    @Override
    public Logger getPiping() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".piping");
    }

    @Override
    public Logger getNavigating() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".navigating");
    }
}
