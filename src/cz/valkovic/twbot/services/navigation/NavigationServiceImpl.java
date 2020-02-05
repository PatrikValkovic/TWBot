package cz.valkovic.twbot.services.navigation;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.services.connectors.NavigationEngine;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NavigationServiceImpl implements NavigationService {

    private NavigationEngine navigation;
    private LoggingService log;

    @Inject
    public NavigationServiceImpl(NavigationEngine navigation, LoggingService log) {
        this.navigation = navigation;
        this.log = log;
    }


    @Override
    public NavigationService queue(String... urls) {
        for (String url : urls) {
            log.getNavigationAction().info("Adding navigation to the queue: " + url);
            this.navigation.setLocation(url);
        }
        return this;
    }
}
