package cz.valkovic.java.twbot.services.connectors.webview;

import cz.valkovic.java.twbot.controls.MyWebView;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.connectors.WebViewConnector;

import javax.inject.Inject;

public class ConnectorImpl implements WebViewConnector {

    private ToNavigationServiceConnector navigationConnector;
    private ToPipesConnectorFactory webViewToPipesFactory;
    private Configuration conf;


    @Inject
    public ConnectorImpl(
            ToNavigationServiceConnector navigationConnector,
            ToPipesConnectorFactory webViewToPipesFactory,
            Configuration conf) {
        this.navigationConnector = navigationConnector;
        this.webViewToPipesFactory = webViewToPipesFactory;
        this.conf = conf;
    }

    @Override
    public void bind(MyWebView view) {
        webViewToPipesFactory.create(view);
        navigationConnector.bind(view);

        view.getEngine().setUserAgent(conf.userAgent());
    }
}
