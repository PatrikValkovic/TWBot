package cz.valkovic.twbot.modules.browsing.controls;

import cz.valkovic.twbot.modules.core.ResourceLoaderService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.tabs.TabRegistrationService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import java.io.IOException;

public class WebViewControl extends VBox{

    @Inject
    public static void register(TabRegistrationService tab) {
        tab.register("Browser", WebViewControl.class, false);

    }

    @Inject
    public WebViewControl(
            ResourceLoaderService resourceLoaderService,
            LoggingService log
    ) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(resourceLoaderService.getResource("controls/MyWebView.fxml"));
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            log.errorMissingFxml(WebViewControl.class, e);
            throw e;
        }
    }
}
