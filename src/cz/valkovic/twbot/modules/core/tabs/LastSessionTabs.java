package cz.valkovic.twbot.modules.core.tabs;

/**
 * Allow to retrieve and store tabs that were (are going to) open last time.
 */
public interface LastSessionTabs {

    /**
     * Get tabs opened in the last session.
     * @return Array of tabs opened last time.
     */
    String[] openedTabs();

    /**
     * Store opened tabs so they can be loaded next time.
     * @param tabs Opened tabs.
     */
    void storeOpenedTabs(String[] tabs);
}
