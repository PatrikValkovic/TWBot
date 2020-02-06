package cz.valkovic.twbot.modules.core.tabs;

import javafx.scene.Node;

/**
 * Allows to retrieve information about registered tabs.
 */
public interface TabsRetrieveService {

    /**
     * Get all possible tab names that can be opened.
     * @return Array with names of all the tabs that have been registered.
     */
    String[] tabsNames();

    /**
     * Get all tabs, that are not closable and thus need's to be opened all the time.
     * @return Array with names of tabs that needs to be opened.
     */
    String[] requiredTabsNames();

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
