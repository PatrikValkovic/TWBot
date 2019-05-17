package cz.valkovic.java.twbot.services.connectors.webview;

import cz.valkovic.java.twbot.controls.MyWebView;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.connectors.WebViewConnector;
import cz.valkovic.java.twbot.services.logging.LoggingService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConnectorImpl implements WebViewConnector {

    private LoggingService log;
    private ToActionServiceConnector actionConnector;
    private Configuration conf;

    private MyWebView view;


    @Inject
    public ConnectorImpl(
            ToActionServiceConnector actionConnector,
            Configuration conf,
            LoggingService log) {
        this.actionConnector = actionConnector;
        this.conf = conf;
        this.log = log;
    }

    @Override
    public void bind(MyWebView view) {
        this.view = view;
        this.view.getEngine().setUserAgent(conf.userAgent());
        actionConnector.bind(view);
    }
}
