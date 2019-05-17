package cz.valkovic.java.twbot;

import cz.valkovic.java.twbot.services.ResourceLoaderService;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.configuration.Configuration;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;

public class Application extends javafx.application.Application {

    @Inject
    private ResourceLoaderService resourceLoaderService;

    @Inject
    private Configuration conf;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ServicesModule.getInjector().injectMembers(this);
        try {
            Parent root = FXMLLoader.load(resourceLoaderService.getResource("views/MainWindow.fxml"));

            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            double width = Math.min(screenSize.getWidth(), conf.windowWidth());
            double height = Math.min(screenSize.getHeight(), conf.windowHeight());


            primaryStage.setTitle("TWBot");
            primaryStage.setScene(new Scene(root, width, height));
            primaryStage.setMaximized(conf.maximalized() || screenSize.getWidth() < conf.windowWidth() || screenSize.getHeight() < conf.windowHeight());
            primaryStage.setFullScreen(conf.fullscreen());
            primaryStage.show();
        }
        catch (IOException exc) {
            ServicesModule.getInjector()
                          .getInstance(LoggingService.class)
                          .errorMissingFxml(Application.class, exc);
            throw exc;
        }

    }
}
