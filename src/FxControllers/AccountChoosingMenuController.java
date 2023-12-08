package src.FxControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import src.Main;
import src.helpers.PlayerProfileChanger;
import src.models.Player;

import java.io.IOException;

public class AccountChoosingMenuController {
    @FXML
    Button CreateNew;
    @FXML
    ListView CharactersList;

    public void initialize() {
        displayAccounts();
        CharactersList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Handle the item clicked action here
                System.out.println("Clicked item: " + newValue);
            }
        });
    }

    private void displayAccounts() {
        for (Player p: PlayerProfileChanger.getAllAccounts()) {
            CharactersList.getItems().add(p);
        }
    }
    public void openCreationMenu() throws IOException {
        Stage stage = (Stage) CreateNew.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent characterCreation = loader.load(Main.class.getResource("graphics/CharacterCreation.fxml"));
        Scene scene = new Scene(characterCreation);
        Main.open(stage, scene);
    }
}
