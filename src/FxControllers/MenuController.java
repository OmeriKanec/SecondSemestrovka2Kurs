package src.FxControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import src.Main;
import src.models.Player;

import java.io.IOException;

public class MenuController {
    @FXML
    Text money;
    @FXML
    Button playBtn;
    @FXML Button userBtn;
    Player player;
    @FXML
    public void initialize() {

    }

    public void openAccountChoosingMenu() {
        Stage stage = (Stage) userBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent characterCreation = loader.load(Main.class.getResource("graphics/AccountChoosingMenu.fxml").openStream());
            Scene scene = new Scene(characterCreation);
            Main.open(stage, scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
        if (player != null) {
            userBtn.textProperty().setValue(player.getUserName());
            money.textProperty().setValue("money: " + player.getMoney());
        }
    }
    public void openLobbyChoosingMenu() {
        Stage stage = (Stage) playBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent lobbyChoosing = loader.load(Main.class.getResource("graphics/LobbyChoosingMenu.fxml").openStream());
            Scene scene = new Scene(lobbyChoosing);
            Main.open(stage, scene);
            LobbyChoosingMenuController lobbyChoosingMenuController = (LobbyChoosingMenuController) loader.getController();
            lobbyChoosingMenuController.setPlayer(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void exit() {
        Platform.exit();
    }

}
