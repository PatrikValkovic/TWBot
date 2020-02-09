package cz.valkovic.twbot.modules.browsing.actions;

import javafx.scene.web.WebEngine;
import java.util.function.Consumer;

/**
 * Handles actions that needs to load the site.
 * The action service waits, after the site is loaded.
 */
public interface ActionsWithReloadService {

    /**
     * Execute action. The action needs to reload the page somehow.
     * @param action Action to execute.
     */
    void action(Consumer<WebEngine> action);

}
