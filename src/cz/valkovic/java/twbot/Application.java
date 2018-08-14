package cz.valkovic.java.twbot;

import cz.valkovic.java.twbot.services.ResourceLoaderService;
import cz.valkovic.java.twbot.services.ServicesModule;
import cz.valkovic.java.twbot.services.logging.LoggingService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;

public class Application extends javafx.application.Application {

    @Inject
    private ResourceLoaderService resourceLoaderService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ServicesModule.getInjector().injectMembers(this);
        try {
            Parent root = FXMLLoader.load(resourceLoaderService.getResource("views/MainWindow.fxml"));

            primaryStage.setTitle("TWBot");
            primaryStage.setScene(new Scene(root, 1280, 768));
            primaryStage.show();
        }
        catch (IOException exc) {
            ServicesModule.getInjector()
                          .getInstance(LoggingService.class)
                          .errorMissingFxml(Application.class, exc)
                          .andExit();
        }

    }
}
