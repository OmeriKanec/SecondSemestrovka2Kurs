package src.FxControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
        if ((username.equals(""))) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("Username can't be empty"));
            Button button = new Button("OK");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.close();
                }
            });
            dialogVbox.getChildren().add(button);
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        } else {
            Player p = new Player(username, STARTINGMONEY);
            PlayerProfileChanger.createAccount(p);
            Stage stage = (Stage) createBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            Parent characterCreation = loader.load(Main.class.getResource("graphics/Menu.fxml").openStream());
            MenuController menuController = loader.getController();
            menuController.setPlayer(p);
            Scene scene = new Scene(characterCreation);
            Main.open(stage, scene);
        }
    }
}
