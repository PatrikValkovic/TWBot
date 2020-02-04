package cz.valkovic.java.twbot.services.connectors;

import com.google.inject.AbstractModule;
import cz.valkovic.java.twbot.services.browserManipulation.Actionable;
import cz.valkovic.java.twbot.services.connectors.webview.ToPipesConnector;
import cz.valkovic.java.twbot.services.connectors.webview.ToPipesConnectorImpl;
import cz.valkovic.java.twbot.services.connectors.webview.WebViewConnector;
import cz.valkovic.java.twbot.services.connectors.webview.WebViewConnectorImpl;
import cz.valkovic.java.twbot.services.navigation.NavigationService;
import cz.valkovic.java.twbot.services.navigation.NavigationServiceImpl;

public class ConnectorsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NavigationEngine.class).to(NavigationEngineImpl.class);
        bind(ToPipesConnector.class).to(ToPipesConnectorImpl.class);
        bind(NavigationService.class).to(NavigationServiceImpl.class);
        bind(WebViewConnector.class).to(WebViewConnectorImpl.class);
        bind(Actionable.class).to(WebViewConnectorImpl.class);

        requestStaticInjection(ToPipesConnectorImpl.class);
    }


}
