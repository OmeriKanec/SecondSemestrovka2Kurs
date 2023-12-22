package src.FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Main;
import src.helpers.Connection;
import src.helpers.ConnectionSingletone;
import src.helpers.PlayerProfileChanger;
import src.models.Player;
import src.models.Room;

import java.io.IOException;
import java.util.List;

public class LobbyChoosingMenuController {
    @FXML
    Button createNew;
    @FXML
    ListView<Room> lobbyList;
    Room room;
    Connection connection;
    List<Room> rooms;
    private Player player;
    public void initialize() {
        connection = ConnectionSingletone.getConnection();
        getAndShowRooms();
        lobbyList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (Room r: rooms) {
                    if (r.toString().equals(newValue.toString())){
                        room = r;
                        break;
                    }
                }
                if (connection.connectToRoom(room.getUuid())) {
                    this.openGame();
                } else {
                    showDialogPopUp();
                    getAndShowRooms();
                }
            }
        });
    }

    public void getAndShowRooms() {
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList();
        rooms = connection.getRooms();
        roomObservableList.addAll(rooms);
        lobbyList.setItems(roomObservableList);
    }
    public void openLobbyCreationMenu() {
        Stage stage = (Stage) createNew.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent lobbyCreation = loader.load(Main.class.getResource("graphics/LobbyCreation.fxml").openStream());
            Scene scene = new Scene(lobbyCreation);
            Main.open(stage, scene);
            LobbyCreationController controller = (LobbyCreationController) loader.getController();
            controller.setPlayer(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void openGame() {
        Stage stage = (Stage) lobbyList.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent lobbyCreation = loader.load(Main.class.getResource("graphics/Game.fxml").openStream());
            Scene scene = new Scene(lobbyCreation);
            Main.open(stage, scene);
            GameController gameController = (GameController) loader.getController();
            room.setCurrentPlayers(room.getCurrentPlayers()+1);
            gameController.setRoom(room);
            gameController.setPlayer(player);
            gameController.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showDialogPopUp() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Game already full"));
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
    }

    public void setPlayer(Player player) {
        this.player = player;
        System.out.println(player.getUserName());
    }
}
