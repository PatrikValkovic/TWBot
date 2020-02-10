package cz.valkovic.twbot.modules.browsing.actions;

import cz.valkovic.twbot.modules.core.logging.LoggingService;
import javax.inject.Inject;

public class NavigationServiceImpl implements NavigationService {

    private final ActionsWithReloadService action;
    private final LoggingService log;

    @Inject
    public NavigationServiceImpl(ActionsWithReloadService action, LoggingService log) {
        this.action = action;
        this.log = log;
    }

    @Override
    public void navigate(String url) {
        this.action.action(engine -> {
            this.log.getAction().debug("Navigating to " + url);
            engine.load(url);
        });
    }
}
