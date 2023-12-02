package src.FxControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import src.Main;

import java.io.IOException;

public class MenuController {
    @FXML
    Text money;
    @FXML
    Button playBtn;
    @FXML Button userBtn;
    @FXML
    public void initialize() {

    }

    public void openCreationMenu() throws IOException {
        Stage stage = (Stage) userBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent characterCreation = loader.load(Main.class.getResource("graphics/CharacterCreation.fxml"));
        Scene scene = new Scene(characterCreation);
        Main.open(stage, scene);
    }
}
