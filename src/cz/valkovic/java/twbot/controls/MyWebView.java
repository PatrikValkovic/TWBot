package cz.valkovic.java.twbot.controls;

import com.google.inject.Inject;
import cz.valkovic.java.twbot.services.ResourceLoaderService;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.connectors.NavigationEngine;
import cz.valkovic.java.twbot.services.connectors.PipeConnectionFactory;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
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
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MyWebView extends VBox implements NavigationEngine {

    @Inject
    private ResourceLoaderService resourceLoaderService;

    @Inject
    private LoggingService log;

    @Inject
    private PipeConnectionFactory pipeConnectionFactory;

    public MyWebView() {
        ServicesModule.getInjector().injectMembers(this);
        try {
            FXMLLoader loader = new FXMLLoader(resourceLoaderService.getResource("controls/MyWebView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException exc) {
            log.errorMissingFxml(MyWebView.class, exc).andExit();
        }

        this.pipeConnectionFactory.create(this);

        this.getEngine().load("https://www.divokekmeny.cz");

        disableBack.bind(Bindings.createBooleanBinding(() -> {
            WebHistory h = this.getEngine().getHistory();
            return h.getCurrentIndex() == 0;
        }, this.webview.getEngine().getHistory().currentIndexProperty()));

        disableForward.bind(Bindings.createBooleanBinding(() -> {
            WebHistory h = this.getEngine().getHistory();
            return h.getCurrentIndex() == h.getEntries().size() - 1 || h.getEntries().size() == 0;
        }, this.webview.getEngine().getHistory().currentIndexProperty()));

        this.getEngine().getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                this.urlfield.setText(this.getEngine().getLocation());
                this.loadedPage.setValue(this.getLocation());
            }
        });
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

    public WebEngine getEngine() {
        return this.webview.getEngine();
    }

    public void setLocation(String url) {
        this.getEngine().load(url);
    }

    private StringProperty loadedPage = new SimpleStringProperty();

    @Override
    public ReadOnlyStringProperty loadedPageProperty(){
        return loadedPage;
    }

    @Override
    public String getLocation() {
        return this.getEngine().getLocation();
    }

    public ReadOnlyStringProperty locationProperty() {
        return this.getEngine().locationProperty();
    }

    @Override
    public String getContent() {
        Document doc = this.getEngine().getDocument();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            ByteArrayOutputStream str = new ByteArrayOutputStream();

            transformer.transform(new DOMSource(doc), new StreamResult(str));
            return new String(str.toByteArray(), StandardCharsets.UTF_8);
        }
        catch (TransformerException ex) {
            log.getParsing().warn("Unable to transform content into string");
            log.getParsing().debug(ex,ex);
            return "";
        }

    }
    //endregion

    //region properties
    @FXML
    private WebView webview;
    @FXML
    private TextField urlfield;

    private BooleanProperty disableBack = new SimpleBooleanProperty(this, "disableBack");

    private BooleanProperty disableForward = new SimpleBooleanProperty(this, "disableForward");
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
            this.setLocation(this.urlfield.getText());
            keyEvent.consume();
        }
    }
    //endregion

    //region methods
    public void refresh() {
        this.getEngine().reload();
    }
    //endregion
}
