package cz.valkovic.twbot.modules.browsing.controls;

import cz.valkovic.twbot.Main;
import cz.valkovic.twbot.modules.browsing.actions.ActionsWithReloadService;
import cz.valkovic.twbot.modules.browsing.actions.NavigationService;
import cz.valkovic.twbot.modules.browsing.engine.WebEngineConnector;
import cz.valkovic.twbot.modules.browsing.events.LocationChangedEvent;
import cz.valkovic.twbot.modules.browsing.events.PageLoadedEvent;
import cz.valkovic.twbot.modules.browsing.setting.BrowserPublicSetting;
import cz.valkovic.twbot.modules.core.actions.ActionsService;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javax.inject.Inject;

public class WebViewController {

    @Inject
    ActionsService actions;
    Object actionsObserverLock;
    @Inject
    WebEngineConnector connector;
    @Inject
    SettingsProviderService setting;
    Object settingLock;
    @Inject
    ActionsWithReloadService actionWithReload;
    @Inject
    NavigationService navigation;
    @Inject
    EventBrokerService events;

    @FXML
    public WebView webview;
    @FXML
    public TextField urlfield;

    @FXML
    protected void initialize() {
        Main.getInjector().injectMembers(this);

        // bind engine to the rest of the application
        connector.bind(this.webview.getEngine());

        // action indicator
        this.actionsObserverLock = this.actions.hasActions().observeInRender(v -> {
            hasActionsProp.setValue(v ? Color.LIGHTGREEN : Color.GREY);
        });

        // bind properties
        cantGoBackProp.bind(Bindings.createBooleanBinding(
                () -> this.webview.getEngine().getHistory().getCurrentIndex() == 0,
                this.webview.getEngine().getHistory().currentIndexProperty()
        ));
        cantGoForwardProp.bind(Bindings.createBooleanBinding(
                () -> {
                    WebHistory h = this.webview.getEngine().getHistory();
                    return h.getCurrentIndex() == h.getEntries().size() - 1 || h.getEntries().size() == 0;
                }, this.webview.getEngine().getHistory().currentIndexProperty()
        ));

        // location
        webview.getEngine().locationProperty().addListener((observ, oldValue, newValue) -> {
            this.events.invoke(new LocationChangedEvent(oldValue, newValue));
            this.urlfield.setText(newValue);
        });
        webview.getEngine().getLoadWorker().stateProperty().addListener((observ, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                this.events.invoke(new PageLoadedEvent(webview.getEngine().getLocation()));
            }
        });

        // navigate to home page
        this.setting.observe(BrowserPublicSetting.class, s -> {
            this.webview.getEngine().load(s.homepage());
        });
        settingLock = this.setting.observe(BrowserPublicSetting.class, s -> {
            this.webview.getEngine().setUserAgent(s.userAgent());
        });
    }

    // are actions in queue
    private Property<Paint> hasActionsProp = new SimpleObjectProperty<>(Color.GREY);
    public Property<Paint> hasActionsProperty() {
        return hasActionsProp;
    }
    public Paint getHasActions() {
        return hasActionsProp.getValue();
    }

    // back action
    @FXML
    private void backAction(ActionEvent ignored) {
        this.webview.getEngine().getHistory().go(-1);
    }
    private BooleanProperty cantGoBackProp = new SimpleBooleanProperty(true);
    public BooleanProperty cantGoBackProperty() {
        return cantGoBackProp;
    }
    public Boolean getCantGoBack() {
        return cantGoBackProp.getValue();
    }

    // forward action
    @FXML
    private void forwardAction(ActionEvent ignored) {
        this.webview.getEngine().getHistory().go(1);
    }
    private BooleanProperty cantGoForwardProp = new SimpleBooleanProperty(true);
    public BooleanProperty cantGoForwardProperty() {
        return cantGoForwardProp;
    }
    public Boolean getCantGoForward() {
        return cantGoForwardProp.getValue();
    }

    // refresh
    @FXML
    private void refreshAction(ActionEvent ignored) {
        this.actionWithReload.action(WebEngine::reload);
    }

    // text field
    @FXML
    private void inputFieldChanged(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            this.navigation.navigate(urlfield.getText());
        }
    }


}
