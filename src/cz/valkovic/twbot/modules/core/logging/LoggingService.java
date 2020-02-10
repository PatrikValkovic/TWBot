package cz.valkovic.twbot.modules.core.logging;

import org.apache.logging.log4j.Logger;

/**
 * Declaration of Logging service.
 * All the logging should go through this interface.
 */
public interface LoggingService {

    /**
     * Logs events with startup of the application or service.
     */
    Logger getStartup();

    /**
     * Logs events related to loading of data, configuration or similar.
     * Should be used for loading during the application, for startup loading use `getStartup()` method.
     */
    Logger getLoading();

    /**
     * Logs events related to resources loading.
     */
    Logger getLoadingResources();

    /**
     * Logs events during application termination.
     */
    Logger getExit();

    /**
     * Logs events related to parsing.
     */
    Logger getParsing();

    /**
     * Logs events related to pipes.
     * Note that parsing logs should rather use `getParsing()` method.
     */
    Logger getPipeping();

    /**
     * Logs event related to execution tasks.
     */
    Logger getExecution();

    /**
     * Logs events related to action (actions in the browser).
     */
    Logger getAction();

    /**
     * Logs events related to events and their processing.
     */
    Logger getEvents();

    /**
     * Logs events related to settings manipulation.
     */
    Logger getSettings();

    /**
     * Logs events related to observable objects.
     */
    Logger getObservable();

    /**
     * Logs events related to database.
     */
    Logger getDatabase();

    /**
     * Logs events in GUI.
     */
    Logger getGUI();

    /**
     * Logs events about timing actions.
     */
    Logger getTiming();

    /**
     * Log error about missing FXML file.
     * @param cls Class that required the file.
     * @param e The exception occurred.
     */
    default void errorMissingFxml(Class<?> cls, Exception e) {
        this.getLoading().error("Cannot load view for " + cls.getCanonicalName());
        this.getLoading().debug(e, e);
    }
}
