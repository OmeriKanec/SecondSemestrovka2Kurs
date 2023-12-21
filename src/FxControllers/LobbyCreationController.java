package src.FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.helpers.ConnectionSingletone;
import src.models.Room;

public class LobbyCreationController {
    @FXML
    TextField name;
    @FXML
    ChoiceBox<Integer> maxPlayers;
    @FXML
    TextField bet;
    @FXML
    ChoiceBox<Integer> bulletCount;
    @FXML
    ChoiceBox<String> deaths;

    public void initialize() {
        ObservableList<Integer> obsMaxPlayers = FXCollections.observableArrayList(2, 3, 4, 5, 6);
        ObservableList<Integer> obsBulletCount = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        ObservableList<String> obsDeaths = FXCollections.observableArrayList("До первой смерти", "До последнего игрока");
        maxPlayers.setItems(obsMaxPlayers);
        bulletCount.setItems(obsBulletCount);
        deaths.setItems(obsDeaths);
        bet.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    bet.setText(newValue.replaceAll("[^\\d]", ""));
                }
        });
    }

    public void createNewLobby() {
        if ((name.getText().equals("")) || (maxPlayers.getValue().equals("")) || (bet.textProperty().getValue().equals("")) ||
                (bulletCount.getValue().equals("")) || (deaths.getValue().equals(""))) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("Fill all inputs"));
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
            Room room = new Room(name.getText(), maxPlayers.getValue(), 0, Integer.parseInt(bet.textProperty().getValue()),
                    bulletCount.getValue(), deaths.getValue());
            ConnectionSingletone.getConnection().sendNewRoomToServer(room);
        }
    }
}
