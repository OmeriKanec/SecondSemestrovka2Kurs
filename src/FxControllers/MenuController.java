package src.FxControllers;

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

    public void openAccountChoosingMenu() throws IOException {
        Stage stage = (Stage) userBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent characterCreation = loader.load(Main.class.getResource("graphics/AccountChoosingMenu.fxml"));
        Scene scene = new Scene(characterCreation);
        Main.open(stage, scene);
    }

    public void setPlayer(Player player) {
        this.player = player;
        if (player != null) {
            userBtn.textProperty().setValue(player.getUserName());
            money.textProperty().setValue("money: " + player.getMoney());
        }
    }

}
