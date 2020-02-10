package cz.valkovic.twbot.modules.core.logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.inject.Singleton;

@Singleton
public class Log4jLoggingService implements LoggingService {

    private static final String BASE_PACKAGE = "cz.valkovic.twbot";

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
    public Logger getPipeping() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".piping");
    }

    public Logger getExecution() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".execution");
    }

    @Override
    public Logger getAction() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".action");
    }

    @Override
    public Logger getEvents() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".events");
    }

    @Override
    public Logger getSettings() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".settings");
    }

    @Override
    public Logger getObservable() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".observable");
    }

    @Override
    public Logger getDatabase() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".database");
    }

    @Override
    public Logger getGUI() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".gui");
    }

    @Override
    public Logger getTiming() {
        return LogManager.getFormatterLogger(BASE_PACKAGE + ".timing");
    }
}
