package cz.valkovic.java.twbot.services.connectors.webview;

import cz.valkovic.java.twbot.controls.MyWebView;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.connectors.WebViewConnector;

import javax.inject.Inject;

public class ConnectorImpl implements WebViewConnector {

    private ToActionServiceConnector actionConnector;
    private ToPipesConnectorFactory webViewToPipesFactory;
    private Configuration conf;


    @Inject
    public ConnectorImpl(
            ToActionServiceConnector actionConnector,
            ToPipesConnectorFactory webViewToPipesFactory,
            Configuration conf) {
        this.actionConnector = actionConnector;
        this.webViewToPipesFactory = webViewToPipesFactory;
        this.conf = conf;
    }

    @Override
    public void bind(MyWebView view) {
        webViewToPipesFactory.create(view);
        actionConnector.bind(view);

        view.getEngine().setUserAgent(conf.userAgent());
    }
}
