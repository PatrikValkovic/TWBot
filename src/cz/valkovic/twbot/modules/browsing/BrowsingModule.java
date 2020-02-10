package cz.valkovic.twbot.modules.browsing;

import com.google.inject.AbstractModule;
import cz.valkovic.twbot.modules.browsing.actions.ActionsWithReloadService;
import cz.valkovic.twbot.modules.browsing.actions.ActionsWithReloadServiceImpl;
import cz.valkovic.twbot.modules.browsing.actions.NavigationService;
import cz.valkovic.twbot.modules.browsing.actions.NavigationServiceImpl;
import cz.valkovic.twbot.modules.browsing.controls.WebViewControl;
import cz.valkovic.twbot.modules.browsing.engine.EngineMiddleware;
import cz.valkovic.twbot.modules.browsing.engine.WebEngineConnector;
import cz.valkovic.twbot.modules.browsing.setting.BrowsingSettingDemand;
import cz.valkovic.twbot.modules.core.actions.WebEngineProvider;
import cz.valkovic.twbot.modules.core.importing.TWModule;

@TWModule(500)
public class BrowsingModule extends AbstractModule {

    @Override
    protected void configure() {
        // register tab
        requestStaticInjection(WebViewControl.class);

        // setting
        requestStaticInjection(BrowsingSettingDemand.class);

        // engine connection
        bind(WebEngineProvider.class).to(EngineMiddleware.class);
        bind(WebEngineConnector.class).to(EngineMiddleware.class);

        // actions
        bind(ActionsWithReloadService.class).to(ActionsWithReloadServiceImpl.class);
        bind(NavigationService.class).to(NavigationServiceImpl.class);

        // browser reloading
        requestStaticInjection(BrowserReloadingRegistration.class);
    }

}
