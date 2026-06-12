package fr.uge.but.schtroumpf.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    /** Dimensions de la fenetre. */
    public static final double WIDTH  = 1200;
    public static final double HEIGHT = 750;

    @Override
    public void start(Stage stage) {
        try {
            BorderPane root = FXMLLoader.load(
                getClass().getResource("WelcomeView.fxml")
            );
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(
                getClass().getResource("application.css").toExternalForm()
            );
            stage.setTitle("Conseil des Schtroumpfs");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
