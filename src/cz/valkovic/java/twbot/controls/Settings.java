package cz.valkovic.java.twbot.controls;

import com.google.inject.Inject;
import cz.valkovic.java.twbot.services.ResourceLoaderService;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import cz.valkovic.java.twbot.services.messaging.MessageService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Settings extends VBox {

    //region injections
    @Inject
    private ResourceLoaderService resourceLoaderService;

    @Inject
    private LoggingService log;

    @Inject
    private MessageService messaging;

    @Inject
    private Configuration conf;
    //endregion


    public Settings() throws IOException {
        // inject members
        ServicesModule.getInjector().injectMembers(this);

        // load template
        try {
            FXMLLoader loader = new FXMLLoader(resourceLoaderService.getResource("controls/Settings.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            log.errorMissingFxml(Settings.class, e);
            throw e;
        }
    }

    @FXML
    void initialize(){
        this.fillValues();
    }

    private void fillValues() {
        field_fullscreen.setSelected(conf.fullscreen());
        field_homepage.setText(conf.homepage());
        field_maximalized.setSelected(conf.maximalized());
        field_navigationTimeMax.setText(Integer.toString(conf.navigationTimeMax()));
        field_navigationTimeMin.setText(Integer.toString(conf.navigationTimeMin()));
        field_parseTime.setText(Integer.toString(conf.parseTime()));
        field_password.setText(conf.password());
        field_reloadPageMax.setText(Integer.toString(conf.reloadPageMax()));
        field_reloadPageMin.setText(Integer.toString(conf.reloadPageMin()));
        field_serverName.setText(conf.serverName());
        field_userAgent.setText(conf.userAgent());
        field_username.setText(conf.username());
        field_windowHeight.setText(Integer.toString(conf.windowHeight()));
        field_windowWidth.setText(Integer.toString(conf.windowWidth()));
    }



    //region properties
    @FXML
    private CheckBox field_fullscreen;
    @FXML
    private TextField field_homepage;
    @FXML
    private CheckBox field_maximalized;
    @FXML
    private TextField field_navigationTimeMax;
    @FXML
    private TextField field_navigationTimeMin;
    @FXML
    private TextField field_parseTime;
    @FXML
    private TextField field_password;
    @FXML
    private TextField field_reloadPageMax;
    @FXML
    private TextField field_reloadPageMin;
    @FXML
    private TextField field_serverName;
    @FXML
    private TextField field_userAgent;
    @FXML
    private TextField field_username;
    @FXML
    private TextField field_windowHeight;
    @FXML
    private TextField field_windowWidth;

    @FXML
    private void save(ActionEvent actionEvent) {

    }

    @FXML
    private void reload(ActionEvent actionEvent) {
        this.fillValues();
    }
    //endregion

}
