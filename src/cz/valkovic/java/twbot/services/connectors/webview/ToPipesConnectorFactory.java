package cz.valkovic.java.twbot.services.connectors.webview;

import cz.valkovic.java.twbot.services.connectors.NavigationEngine;

public interface ToPipesConnectorFactory {

    ToPipesConnector create(NavigationEngine engine);

}
