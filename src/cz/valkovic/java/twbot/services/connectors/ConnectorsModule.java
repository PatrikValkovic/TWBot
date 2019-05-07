package cz.valkovic.java.twbot.services.connectors;

import com.google.inject.AbstractModule;
import cz.valkovic.java.twbot.services.browserManipulation.ActionServiceImpl;
import cz.valkovic.java.twbot.services.browserManipulation.ActionsService;
import cz.valkovic.java.twbot.services.connectors.webview.ConnectorImpl;
import cz.valkovic.java.twbot.services.connectors.webview.ToActionServiceConnector;
import cz.valkovic.java.twbot.services.connectors.webview.ToPipesConnector;
import cz.valkovic.java.twbot.services.connectors.webview.ToPipesConnectorImpl;
import cz.valkovic.java.twbot.services.navigation.NavigationMiddleware;
import cz.valkovic.java.twbot.services.navigation.NavigationService;

public class ConnectorsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NavigationEngine.class).to(NavigationEngineImpl.class);
        bind(ToPipesConnector.class).to(ToPipesConnectorImpl.class);
        bind(NavigationMiddleware.class).to(NavigationService.class);
        bind(WebViewConnector.class).to(ConnectorImpl.class);
        bind(ActionsService.class).to(ActionServiceImpl.class);
        bind(ToActionServiceConnector.class).to(ActionsService.class);

        requestStaticInjection(ToPipesConnectorImpl.class);
    }


}
