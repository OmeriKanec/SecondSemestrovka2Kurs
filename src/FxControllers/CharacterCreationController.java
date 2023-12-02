package src.FxControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.Main;
import src.helpers.PlayerProfileChanger;
import src.models.Player;

import java.io.IOException;

public class CharacterCreationController {
    @FXML
    TextField UsernameInput;
    @FXML
    Button createBtn;
    private static final int STARTINGMONEY = 500;
    @FXML
    public void initialize() {

    }

    public void create() throws IOException {
        String username = UsernameInput.textProperty().getValue();
        Player p = new Player(username, STARTINGMONEY);
        PlayerProfileChanger.createAccount(p);
        Stage stage = (Stage) createBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent characterCreation = loader.load(Main.class.getResource("graphics/Menu.fxml"));
        Scene scene = new Scene(characterCreation);
        Main.open(stage, scene);
    }
}
