package cz.valkovic.twbot.modules.browsing.actions;

import javax.inject.Inject;

public class NavigationServiceImpl implements NavigationService {

    private final ActionsWithReloadService action;

    @Inject
    public NavigationServiceImpl(ActionsWithReloadService action) {
        this.action = action;
    }

    @Override
    public void navigate(String url) {
        this.action.action(engine -> engine.load(url));
    }
}
