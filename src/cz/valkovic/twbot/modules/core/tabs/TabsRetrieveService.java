package cz.valkovic.twbot.modules.core.tabs;

import javafx.scene.Node;

/**
 * Allows to retrieve information about registered tabs.
 */
public interface TabsRetrieveService {

    /**
     * Get all possible tab names.
     * @return Array with names of all the tabs that have been registered.
     */
    String[] tabsNames();

    /**
     * Return class that represent tab with concrete tab.
     * @param name Name of the tab.
     * @return Class representing tab.
     */
    Class<? extends Node> getRepresentativeClass(String name);

    /**
     * Check if should be tab with specific name closable.
     * @param name Name of the tab.
     * @return True if the tab should be closable, false otherwise.
     */
    boolean isClosable(String name);
}
