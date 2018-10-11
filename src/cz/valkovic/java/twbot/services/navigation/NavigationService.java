package cz.valkovic.java.twbot.services.navigation;

import cz.valkovic.java.twbot.services.browserManipulation.ActionMiddleware;
import cz.valkovic.java.twbot.services.configuration.InterConfiguration;
import cz.valkovic.java.twbot.services.logging.LoggingService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NavigationService implements NavigationMiddleware {

    private ActionMiddleware actions;
    private LoggingService log;

    @Inject
    public NavigationService(ActionMiddleware actions, LoggingService log) {
        this.actions = actions;
        this.log = log;
    }



    @Override
    public NavigationMiddleware queue(String... urls) {
        for (String url : urls) {
            log.getNavigationAction().info("Adding navigation to the queue: " + url);
            actions.performAction(webEngine -> {
                log.getNavigationAction().info("Navigating to " + url);
                webEngine.load(url);
            });
        }
        return this;
    }
}
