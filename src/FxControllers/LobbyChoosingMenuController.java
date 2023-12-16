package src.FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import src.Main;
import src.helpers.Connection;
import src.helpers.ConnectionSingletone;
import src.models.Room;

import java.io.IOException;

public class LobbyChoosingMenuController {
    @FXML
    Button createNew;
    @FXML
    ListView<Room> lobbyList;
    Room room;
    //Connection connection = ConnectionSingletone.getConnection();
    public void initialize() {
        getAndShowRooms();
    }

    private void getAndShowRooms() {
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList();
       // roomObservableList.addAll(connection.getRooms());
        lobbyList.setItems(roomObservableList);
    }
    public void openLobbyCreationMenu() {
        Stage stage = (Stage) createNew.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent lobbyCreation = loader.load(Main.class.getResource("graphics/LobbyCreation.fxml").openStream());
            Scene scene = new Scene(lobbyCreation);
            Main.open(stage, scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
