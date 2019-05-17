package cz.valkovic.java.twbot.services.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLoggingService implements LoggingService {

    private static Logger log = LogManager.getLogger(TestLoggingService.class);

    @Override
    public Logger getStartup() {
        return log;
    }

    @Override
    public Logger getLoading() {
        return log;
    }

    @Override
    public Logger getLoadingResources() {
        return log;
    }

    @Override
    public Logger getExit() {
        return log;
    }

    @Override
    public Logger getParsing() {
        return log;
    }

    @Override
    public Logger getPiping() {
        return log;
    }

    @Override
    public Logger getAction() {
        return log;
    }

    @Override
    public Logger getNavigationAction() {
        return log;
    }

    @Override
    public Logger getMessaging() {
        return log;
    }

    @Override
    public Logger getSettings() {
        return log;
    }
}
