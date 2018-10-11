package cz.valkovic.java.twbot.services.connectors;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import cz.valkovic.java.twbot.services.browserManipulation.ActionMiddleware;
import cz.valkovic.java.twbot.services.browserManipulation.ActionService;
import cz.valkovic.java.twbot.services.browserManipulation.Actionable;
import cz.valkovic.java.twbot.services.connectors.webview.*;
import cz.valkovic.java.twbot.services.navigation.NavigationMiddleware;
import cz.valkovic.java.twbot.services.navigation.NavigationService;

public class ConnectorsModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(ToPipesConnector.class, ToPipesConnectorImpl.class)
                .build(ToPipesConnectorFactory.class));

        bind(NavigationMiddleware.class).to(NavigationService.class);
        bind(WebViewConnector.class).to(ConnectorImpl.class);
        bind(ActionMiddleware.class).to(ActionService.class);
        bind(ToActionServiceConnector.class).to(ActionMiddleware.class);
    }


}
