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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Main;
import src.helpers.ConnectionSingletone;
import src.models.Player;
import src.models.Room;

import java.io.IOException;

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
    private Room room;
    private Player player;

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
             room = new Room(name.getText(), maxPlayers.getValue(), 0, Integer.parseInt(bet.textProperty().getValue()),
                    bulletCount.getValue(), deaths.getValue());
            ConnectionSingletone.getConnection().sendNewRoomToServer(room);
            openGame();
        }
    }
    public void openGame() {
        Stage stage = (Stage) name.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent lobbyCreation = loader.load(Main.class.getResource("graphics/Game.fxml").openStream());
            Scene scene = new Scene(lobbyCreation);
            Main.open(stage, scene);
            GameController gameController = (GameController) loader.getController();
            gameController.setRoom(room);
            gameController.setPlayer(player);
            gameController.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
