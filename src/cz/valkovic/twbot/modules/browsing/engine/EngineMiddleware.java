package cz.valkovic.twbot.modules.browsing.engine;

import cz.valkovic.twbot.modules.core.actions.WebEngineProvider;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import javafx.scene.web.WebEngine;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Connects web browser (the engine) with the rest of the application.
 */
@Singleton
public class EngineMiddleware implements WebEngineProvider, WebEngineConnector {

    private final LoggingService log;
    private WebEngine engine = null;

    @Inject
    public EngineMiddleware(LoggingService log) {
        this.log = log;
    }

    @Override
    public void bind(WebEngine engine) {
        this.log.getStartup().debug("WebEngine bind");
        this.engine = engine;
    }

    @Override
    public WebEngine getEngine() {
        return engine;
    }
}
