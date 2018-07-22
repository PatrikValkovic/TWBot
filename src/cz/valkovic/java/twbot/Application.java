package cz.valkovic.java.twbot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(ResourcesLoader.getResource("views/MainWindow.fxml"));

            primaryStage.setTitle("TWBot");
            primaryStage.setScene(new Scene(root, 1280, 768));
            primaryStage.show();
        }
        catch(IOException exc){
            System.out.println("Exception");
            System.out.println(exc.toString());
            exc.printStackTrace();
        }

    }
}
