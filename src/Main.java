package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.FxControllers.MenuController;
import src.models.Player;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("graphics/AccountChoosingMenu.fxml").openStream());
        primaryStage.setTitle("RussianRoulette");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void open(Stage stage, Scene scene) throws IOException {
        stage.setScene(scene);
    }
}
