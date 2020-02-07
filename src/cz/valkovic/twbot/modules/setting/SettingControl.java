package cz.valkovic.twbot.modules.setting;

import cz.valkovic.twbot.modules.core.ResourceLoaderService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

public class SettingControl extends VBox {

    @Inject
    public SettingControl(
            ResourceLoaderService resourceLoaderService,
            LoggingService log
    ) throws IOException {
        try {
            URL styles = resourceLoaderService.getResource("controls/Settings.css");
            FXMLLoader loader = new FXMLLoader(resourceLoaderService.getResource("controls/Settings.fxml"));
            loader.setRoot(this);
            loader.load();
            this.getStylesheets().add(styles.toExternalForm());
        }
        catch (IOException e) {
            log.errorMissingFxml(SettingControl.class, e);
            throw e;
        }
    }

}
