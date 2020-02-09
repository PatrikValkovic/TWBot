package cz.valkovic.twbot.modules.browsing.engine;

import javafx.scene.web.WebEngine;

/**
 * Allows to connect WebEngine with the rest of the application.
 */
public interface WebEngineConnector {

    /**
     * Bind WebEngine to the rest of the application.
     * @param engine Engine that will be used by the application.
     */
    void bind(WebEngine engine);

}
