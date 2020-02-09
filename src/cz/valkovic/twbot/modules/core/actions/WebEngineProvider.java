package cz.valkovic.twbot.modules.core.actions;

import javafx.scene.web.WebEngine;

/**
 * Interface that needs to be implemented in order to execute actions using {@link WebEngine}.
 */
public interface WebEngineProvider {

    /**
     * Returns web engine.
     * @return Web engine to execute actions.
     */
    WebEngine getEngine();

}
