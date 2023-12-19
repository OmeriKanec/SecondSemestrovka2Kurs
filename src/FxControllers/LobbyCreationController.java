package src.FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
        int deathsBeforeFinish;
        if (deaths.getValue().equals("До первой смерти")){
            deathsBeforeFinish = 1;
        } else {
            deathsBeforeFinish = maxPlayers.getValue() - 1;
        }
        Room room = new Room(name.getText(), maxPlayers.getValue(), 1, Integer.parseInt(bet.textProperty().getValue()),
                bulletCount.getValue(), deathsBeforeFinish);
        ConnectionSingletone.getConnection().sendNewRoomToServer(room);
    }
}
