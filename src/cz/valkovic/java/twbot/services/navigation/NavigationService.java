package cz.valkovic.java.twbot.services.navigation;

import cz.valkovic.java.twbot.services.connectors.NavigationEngine;
import cz.valkovic.java.twbot.services.logging.LoggingService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NavigationService implements NavigationMiddleware {

    private NavigationEngine navigation;
    private LoggingService log;

    @Inject
    public NavigationService(NavigationEngine navigation, LoggingService log) {
        this.navigation = navigation;
        this.log = log;
    }


    @Override
    public NavigationMiddleware queue(String... urls) {
        for (String url : urls) {
            log.getNavigationAction().info("Adding navigation to the queue: " + url);
            this.navigation.setLocation(url);
        }
        return this;
    }
}
