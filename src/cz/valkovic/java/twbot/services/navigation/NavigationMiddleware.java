package cz.valkovic.java.twbot.services.navigation;

import cz.valkovic.java.twbot.services.connectors.webview.ToNavigationServiceConnector;

import java.net.URL;
import java.util.Arrays;

public interface NavigationMiddleware extends ToNavigationServiceConnector {

    NavigationMiddleware queue(String... urls);

    default NavigationMiddleware queue(URL... urls) {
        return this.queue(Arrays.stream(urls).map(URL::toString).toArray(String[]::new));
    }

}
