package cz.valkovic.java.twbot.controls;

import com.google.inject.Inject;
import cz.valkovic.java.twbot.services.ResourceLoaderService;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.connectors.NavigationEngine;
import cz.valkovic.java.twbot.services.connectors.NavigationEngineImpl;
import cz.valkovic.java.twbot.services.connectors.webview.WebViewConnector;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import cz.valkovic.java.twbot.services.messaging.messages.WebLoaded;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.io.IOException;

public class MyWebView extends VBox{

    //region injections
    @Inject
    private ResourceLoaderService resourceLoaderService;

    @Inject
    private LoggingService log;

    @Inject
    private WebViewConnector connector;

    @Inject
    private MessageService messaging;

    @Inject
    private NavigationEngine nav;

    @Inject
    private Configuration conf;
    //endregion


    public MyWebView() throws IOException {
        // inject members
        ServicesModule.getInjector().injectMembers(this);

        // load template
        try {
            FXMLLoader loader = new FXMLLoader(resourceLoaderService.getResource("controls/MyWebView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            log.errorMissingFxml(MyWebView.class, e);
            throw e;
        }

        // connection with rest of the app
        this.connector.bind(this);

        // navigate to index page
        this.getEngine().load(this.conf.homepage());

        // bind properties
        bindProperties();

        // handle when engine navigates
        this.getEngine().getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                this.urlfield.setText(this.getEngine().getLocation());
                this.messaging.invoke(new WebLoaded(
                        NavigationEngineImpl.contentFromEngine(this.getEngine(), this.log),
                        this.getEngine().getLocation()
                ));
            }
        });
    }

    public WebEngine getEngine() {
        return this.webview.getEngine();
    }

    //region getters setters
    public boolean isDisableBack() {
        return disableBack.get();
    }

    public BooleanProperty disableBackProperty() {
        return disableBack;
    }

    public void setDisableBack(boolean disableBack) {
        this.disableBack.set(disableBack);
    }

    public boolean isDisableForward() {
        return disableForward.get();
    }

    public BooleanProperty disableForwardProperty() {
        return disableForward;
    }

    public void setDisableForward(boolean disableForward) {
        this.disableForward.set(disableForward);
    }

    public ReadOnlyStringProperty locationProperty() {
        return this.getEngine().locationProperty();
    }
    //endregion

    //region properties
    @FXML
    private WebView webview;
    @FXML
    private TextField urlfield;

    private BooleanProperty disableBack = new SimpleBooleanProperty(this, "disableBack");

    private BooleanProperty disableForward = new SimpleBooleanProperty(this, "disableForward");

    private void bindProperties()
    {
        disableBack.bind(Bindings.createBooleanBinding(() -> {
            WebHistory h = this.getEngine().getHistory();
            return h.getCurrentIndex() == 0;
        }, this.webview.getEngine().getHistory().currentIndexProperty()));

        disableForward.bind(Bindings.createBooleanBinding(() -> {
            WebHistory h = this.getEngine().getHistory();
            return h.getCurrentIndex() == h.getEntries().size() - 1 || h.getEntries().size() == 0;
        }, this.webview.getEngine().getHistory().currentIndexProperty()));
    }
    //endregion

    //region handlers
    @FXML
    private void backClicked(ActionEvent actionEvent) {
        WebHistory h = this.getEngine().getHistory();
        if (h.getCurrentIndex() > 0)
            h.go(-1);
    }

    @FXML
    private void forwardClicked(ActionEvent actionEvent) {
        WebHistory h = this.getEngine().getHistory();
        if (h.getCurrentIndex() < h.getEntries().size() - 1)
            h.go(1);
    }

    @FXML
    private void refreshClicked(ActionEvent actionEvent) {
        this.getEngine().reload();
    }

    @FXML
    private void fieldInput(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            this.nav.setLocation(this.urlfield.getText());
            keyEvent.consume();
        }
    }
    //endregion
}
