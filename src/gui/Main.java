package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage window;

    @Override
    public void start(Stage primaryStage) throws IOException {

        window = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("resources/WireWorldView.fxml"));
        Scene main = new Scene(root);
        window.setScene(main);
        window.setResizable(false);
        window.sizeToScene();
        window.setTitle("World of Wires");

        window.show();
    }
}
