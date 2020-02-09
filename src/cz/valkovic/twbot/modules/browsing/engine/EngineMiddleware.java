package cz.valkovic.twbot.modules.browsing.engine;

import cz.valkovic.twbot.modules.core.actions.WebEngineProvider;
import javafx.scene.web.WebEngine;
import javax.inject.Singleton;

/**
 * Connects web browser (the engine) with the rest of the application.
 */
@Singleton
public class EngineMiddleware implements WebEngineProvider, WebEngineConnector {

    private WebEngine engine = null;

    @Override
    public void bind(WebEngine engine) {
        this.engine = engine;
    }

    @Override
    public WebEngine getEngine() {
        return engine;
    }
}
