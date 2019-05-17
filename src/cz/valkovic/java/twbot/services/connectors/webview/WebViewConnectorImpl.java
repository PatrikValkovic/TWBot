package cz.valkovic.java.twbot.services.connectors.webview;

import cz.valkovic.java.twbot.controls.MyWebView;
import cz.valkovic.java.twbot.services.browserManipulation.Actionable;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.WebLoaded;
import javafx.scene.web.WebEngine;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WebViewConnectorImpl implements WebViewConnector, Actionable {

    private LoggingService log;
    private Configuration conf;

    private MyWebView view = null;


    @Inject
    public WebViewConnectorImpl(
            Configuration conf,
            LoggingService log,
            MessageService messages) {
        this.conf = conf;
        this.log = log;

        messages.listenTo(WebLoaded.class, e -> {
            synchronized (this.actionMonitor){
                this.actionMonitor.notifyAll();
            }
        });
    }

    @Override
    public void bind(MyWebView view) {
        this.view = view;
        this.getEngine().setUserAgent(conf.userAgent());
        this.log.getLoading().debug("WebView binded to " + getClass().getSimpleName());
    }

    @Override
    public WebEngine getEngine() {
        return this.view.getEngine();
    }

    private final Object actionMonitor = new Object();

    @Override
    public Object getActionMonitor() {
        return this.actionMonitor;
    }

    @Override
    public void waitForMonitor() throws InterruptedException {
        synchronized (this.actionMonitor){
            this.actionMonitor.wait(this.conf.maxLockWaitingTime());
        }
    }
}
