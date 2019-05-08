package cz.valkovic.java.twbot.services.connectors;

import com.google.inject.AbstractModule;
import cz.valkovic.java.twbot.services.browserManipulation.ActionsService;
import cz.valkovic.java.twbot.services.connectors.webview.*;
import cz.valkovic.java.twbot.services.navigation.NavigationMiddleware;
import cz.valkovic.java.twbot.services.navigation.NavigationService;

public class ConnectorsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NavigationEngine.class).to(NavigationEngineImpl.class);
        bind(ToPipesConnector.class).to(ToPipesConnectorImpl.class);
        bind(NavigationMiddleware.class).to(NavigationService.class);
        bind(WebViewConnector.class).to(WebViewConnectorImpl.class);
        bind(ToActionServiceConnector.class).to(ActionsService.class);

        requestStaticInjection(ToPipesConnectorImpl.class);
    }


}
