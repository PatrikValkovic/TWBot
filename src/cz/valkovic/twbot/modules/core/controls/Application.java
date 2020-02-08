package cz.valkovic.twbot.modules.core.controls;

import cz.valkovic.twbot.Main;
import cz.valkovic.twbot.modules.core.ResourceLoaderService;
import cz.valkovic.twbot.modules.core.events.EventBrokerService;
import cz.valkovic.twbot.modules.core.logging.LoggingService;
import cz.valkovic.twbot.modules.core.settings.SettingsProviderService;
import cz.valkovic.twbot.modules.core.settings.instances.CorePublicSetting;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.inject.Inject;
import java.io.IOException;

/**
 * JavaFX application.
 */
public class Application extends javafx.application.Application {
    @Inject
    private ResourceLoaderService resourceLoaderService;
    @Inject
    private SettingsProviderService setting;
    @Inject
    private EventBrokerService event;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.getInjector().injectMembers(this);

        Parent root;
        try {
            root = FXMLLoader.load(resourceLoaderService.getResource("views/MainWindow.fxml"));
        }
        catch (IOException exc) {
            Main.getInjector()
                .getInstance(LoggingService.class)
                .errorMissingFxml(Application.class, exc);
            throw exc;
        }

        setting.observeInRender(CorePublicSetting.class, s -> {
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            double width = Math.min(screenSize.getWidth(), s.windowWidth());
            double height = Math.min(screenSize.getHeight(), s.windowHeight());

            primaryStage.setTitle("TWBot");
            primaryStage.setScene(new Scene(root, width, height));
            primaryStage.setMaximized(
                    s.maximalized() ||
                    screenSize.getWidth() < s.windowWidth() ||
                    screenSize.getHeight() < s.windowHeight()
            );
            primaryStage.setFullScreen(s.fullscreen());
            primaryStage.show();

            event.invoke(new ApplicationShowEvent());
        });
    }
}
