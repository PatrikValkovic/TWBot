package cz.valkovic.twbot.controls;

import com.google.inject.Inject;
import cz.valkovic.twbot.Main;
import cz.valkovic.twbot.modules.core.ResourceLoaderService;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.services.configuration.Configuration;
import cz.valkovic.twbot.services.messaging.messages.SettingsChangedAttempt;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Settings extends VBox {

    //region injections
    @Inject
    private ResourceLoaderService resourceLoaderService;

    @Inject
    private LoggingService log;

    @Inject
    private EventBrokerService messaging;

    @Inject
    private Configuration conf;
    //endregion


    public Settings() throws IOException {
        // inject members
        Main.getInjector().injectMembers(this);

        // load template
        try {
            URL styles = resourceLoaderService.getResource("controls/Settings.css");
            FXMLLoader loader = new FXMLLoader(resourceLoaderService.getResource("controls/Settings.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
            this.getStylesheets().add(styles.toExternalForm());
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

    private int parse(TextField field, int original){
        try {
            int val = Integer.parseInt(field.getText());
            field.getStyleClass().removeAll("error");
            return val;
        } catch(NumberFormatException e){
            field.getStyleClass().add("error");
        }
        return original;
    }

    @FXML
    private void save(ActionEvent actionEvent) {
        log.getSettings().debug("Parsing settings");
        boolean fullscreen = field_fullscreen.isSelected();
        String homepage = field_homepage.getText();
        boolean maximalized = field_maximalized.isSelected();
        String password = field_password.getText();
        String serverName = field_serverName.getText();
        String userAgent = field_userAgent.getText();
        String username = field_username.getText();
        int navigationTimeMax = this.parse(field_navigationTimeMax, conf.navigationTimeMax());
        int navigationTimeMin = this.parse(field_navigationTimeMin, conf.navigationTimeMin());
        int parseTime = this.parse(field_parseTime, conf.parseTime());
        int reloadPageMax = this.parse(field_reloadPageMax, conf.reloadPageMax());
        int reloadPageMin = this.parse(field_reloadPageMin, conf.reloadPageMin());
        int windowHeight = this.parse(field_windowHeight, conf.windowHeight());
        int windowWidth = this.parse(field_windowWidth, conf.windowWidth());

        messaging.invoke(new SettingsChangedAttempt(
                fullscreen,
                homepage,
                maximalized,
                navigationTimeMax,
                navigationTimeMin,
                parseTime,
                password,
                reloadPageMax,
                reloadPageMin,
                serverName,
                userAgent,
                username,
                windowHeight,
                windowWidth
        ));
    }

    @FXML
    private void reload(ActionEvent actionEvent) {
        this.fillValues();
    }
    //endregion

}
