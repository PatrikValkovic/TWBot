package cz.valkovic.twbot.services.logging;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
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
    public Logger getPipeping() {
        return log;
    }

    @Override
    public Logger getExecution() {
        return log;
    }

    @Override
    public Logger getAction() {
        return log;
    }

    @Override
    public Logger getEvents() {
        return log;
    }

    @Override
    public Logger getSettings() {
        return log;
    }

    @Override
    public Logger getObservable() {
        return log;
    }

    @Override
    public Logger getDatabase() {
        return log;
    }

    @Override
    public Logger getGUI() {
        return log;
    }

    @Override
    public Logger getTiming() {
        return log;
    }
}
