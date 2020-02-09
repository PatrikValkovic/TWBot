package cz.valkovic.twbot.modules.browsing.actions;

import java.net.URL;

/**
 * Handles navigation of the web browser.
 */
public interface NavigationService {

    /**
     * Navigate to specific location.
     * @param url Location to navigate to.
     */
    void navigate(String url);

    /**
     * Navigates to specific location.
     * @param url Location to navigate to.
     */
    default void navigate(URL url){
        this.navigate(url.toString());
    }

}
