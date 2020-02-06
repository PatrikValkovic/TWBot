package cz.valkovic.twbot.modules.core.tabs;

import javafx.scene.Node;

/**
 * Register possible tab, that can user open.
 */
public interface TabRegistrationService {

    /**
     * Register tab to the application.
     * @param title Title of the tab
     * @param control Control that represent the content of the tab.
     * @param closable True if the tab should be closable, false if the tab should be always visible.
     */
    void register(String title, Class<? extends Node> control, boolean closable);

    /**
     * Register tab to the application.
     * @param title Title of the tab
     * @param control Control that represent the content of the tab.
     */
    default void register(String title, Class<? extends Node> control){
        this.register(title, control, true);
    }

    /**
     * Register tab to the application.
     * The name of the tab will be name of the class.
     * @param control Control that represent the content of the tab.
     */
    default void register(Class<? extends Node> control){
        this.register(control.getSimpleName(), control);
    }

}
