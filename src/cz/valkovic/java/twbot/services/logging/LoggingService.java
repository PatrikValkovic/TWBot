package cz.valkovic.java.twbot.services.logging;

import org.apache.logging.log4j.Logger;

public interface LoggingService {

    Logger getStartup();

    Logger getLoading();

    Logger getLoadingResources();

    Logger getExit();

    Logger getParsing();

    Logger getPiping();

    Logger getAction();

    Logger getNavigationAction();

    Logger getMessaging();

    default void errorMissingFxml(Class<?> cls, Exception e) {
        this.getLoading().error("Cannot load view for " + cls.getCanonicalName());
        this.getLoading().debug(e, e);
    }
}
