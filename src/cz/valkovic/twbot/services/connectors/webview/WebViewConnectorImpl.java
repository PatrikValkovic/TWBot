package cz.valkovic.twbot.services.connectors.webview;

import cz.valkovic.twbot.controls.MyWebView;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePrivateSetting;
import cz.valkovic.twbot.modules.core.settings.instances.CorePublicSetting;
import cz.valkovic.twbot.services.browserManipulation.Actionable;
import cz.valkovic.twbot.services.messaging.messages.WebLoaded;
import javafx.scene.web.WebEngine;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WebViewConnectorImpl implements WebViewConnector, Actionable {

    private LoggingService log;

    @SuppressWarnings("FieldCanBeLocal")
    private Object corePublicSettingLock;
    private CorePublicSetting setting;
    private CorePrivateSetting privSetting;


    private MyWebView view = null;


    @Inject
    public WebViewConnectorImpl(
            SettingsProviderService settingProvider,
            LoggingService log,
            EventBrokerService messages) {
        this.log = log;

        corePublicSettingLock = settingProvider.observe(
                CorePublicSetting.class,
                s -> {
                    this.setting = s;
                    if(this.view != null && this.getEngine() != null)
                        this.getEngine().setUserAgent(s.userAgent());
                }
        );
        settingProvider.observe(CorePrivateSetting.class, s -> this.privSetting = s);

        messages.listenTo(WebLoaded.class, e -> {
            synchronized (this.actionMonitor){
                this.actionMonitor.notifyAll();
            }
        });
    }

    @Override
    public void bind(MyWebView view) {
        this.view = view;
        this.getEngine().setUserAgent(setting.userAgent());
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
            this.actionMonitor.wait(this.privSetting.maxLockWaitingTime());
        }
    }
}
