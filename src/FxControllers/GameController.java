package src.FxControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import src.Main;
import src.helpers.Connection;
import src.helpers.ConnectionSingletone;
import src.helpers.PlayerProfileChanger;
import src.models.Player;
import src.models.Room;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController extends Thread {
    @FXML
    ImageView table;
    @FXML
    ImageView seat1;
    @FXML
    ImageView seat2;
    @FXML
    ImageView seat3;
    @FXML
    ImageView seat4;
    @FXML
    ImageView seat5;
    @FXML
    ImageView seat6;
    @FXML
    AnchorPane pane;
    @FXML
    ImageView revolver;
    private Connection connection;
    private Room room;
    private boolean gameStarted;
    private boolean gameGoing;
    private int activePlayer;
    private int playerNumber;
    private List<ImageView> seats;
    private int[] deadOrAlive;
    private int deadCount;
    private Image playerImage = new Image(new File("SecondSemestrovka2Kurs\\src\\assets\\player.jpg").toURI().toString());
    private Image deadImage = new Image(new File("SecondSemestrovka2Kurs\\src\\assets\\dead.png").toURI().toString());
    private Image revolverImage = new Image(new File("SecondSemestrovka2Kurs\\src\\assets\\revolver.png").toURI().toString());
    private Player player;
    private boolean playerAlive;
    private int bulletsLeft;

    public void initialize() {
        seats = new ArrayList<>();
        seats.add(seat1);
        seats.add(seat2);
        seats.add(seat3);
        seats.add(seat4);
        seats.add(seat5);
        seats.add(seat6);
        connection = ConnectionSingletone.getConnection();
        Image tableImage = new Image(new File("SecondSemestrovka2Kurs\\src\\assets\\table.jpg").toURI().toString());
        table.imageProperty().setValue(tableImage);
        int currentPlayers = connection.getCurrentPlayers();
        playerNumber = currentPlayers - 1;
        for (int i = 0; i < currentPlayers; i++) {
            seats.get(i).imageProperty().setValue(playerImage);
        }
    }

    @Override
    public void run() {
        gameStarted = false;
        gameGoing = false;
        activePlayer = 0;
        deadCount = 0;
        playerAlive = true;
        bulletsLeft = room.getBulletsCount();
        deadOrAlive = new int[room.getMaxPlayers()];
        revolver.imageProperty().setValue(revolverImage);
        room.setCurrentPlayers(connection.getCurrentPlayers());
        PlayerProfileChanger.changeMoney(player, player.getMoney()-room.getBet());
        for (int i = 0; i < deadOrAlive.length; i++) {
            deadOrAlive[i] = 1;
        }
        while (!gameStarted) {
            System.out.println("cur: " + room.getCurrentPlayers() + " max: " + room.getMaxPlayers());
            if (playerNumber != room.getMaxPlayers() - 1) {
                room.setCurrentPlayers(connection.waitForServerToSendCurrentPlayers());
            }
            for (int i = 0; i < room.getCurrentPlayers(); i++) {
                seats.get(i).imageProperty().setValue(playerImage);
            }
            if (room.getCurrentPlayers() == room.getMaxPlayers()) {
                gameStarted = true;
                gameGoing = true;
            }
        }
        pane.getScene().setOnKeyPressed(event -> {
            if (gameGoing && activePlayer == playerNumber) {
                if (event.getCode().toString().equals("R")) {
                    connection.sendCommandToSpin();
                }
            }
            if (!gameGoing){
                end();
            }
        });
        pane.getScene().setOnMouseClicked(event -> {
            if (gameGoing && activePlayer == playerNumber) {
                connection.sendCommandToShoot();
            }
            if (!gameGoing){
                end();
            }
        });
        while (gameGoing) {
            switch (activePlayer) {
                case 0:
                    revolver.rotateProperty().setValue(45);
                    break;
                case 1:
                    revolver.rotateProperty().setValue(135);
                    break;
                case 2:
                    revolver.rotateProperty().setValue(315);
                    break;
                case 3:
                    revolver.rotateProperty().setValue(225);
                    break;
                case 4:
                    revolver.rotateProperty().setValue(0);
                    break;
                case 5:
                    revolver.rotateProperty().setValue(180);
                    break;
            }
            if (activePlayer == playerNumber) {
                String mes = connection.listenForStateChanges();
                if (mes.equals("alive")) {
                    nextTurn();
                } else if (mes.split(":")[0].equals("dead")) {
                    deadOrAlive[activePlayer] = 0;
                    seats.get(activePlayer).imageProperty().setValue(deadImage);
                    deadCount++;
                    bulletsLeft--;
                    playerAlive = false;
                    nextTurn();
                }
            } else {
                String[] mes = connection.listenForStateChanges().split(":");
                if (mes[0].equals("spin")) {

                } else if (mes[0].equals("alive")) {

                    nextTurn();
                } else if (mes[0].equals("dead")) {
                    deadOrAlive[activePlayer] = 0;
                    seats.get(activePlayer).imageProperty().setValue(deadImage);
                    deadCount++;
                    bulletsLeft--;
                    nextTurn();
                }
            }
        }
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    private void nextTurn() {
        int inc = 1;
        while (deadOrAlive[(activePlayer + inc) % deadOrAlive.length] == 0) {
            inc++;
        }
        activePlayer = (activePlayer + inc) % deadOrAlive.length;
        if (room.getDeathsBeforeFinish().equals("До первой смерти")) {
            if (deadCount > 0 || bulletsLeft == 0) {
                gameGoing = false;
            }
        } else {
            if (deadCount == room.getMaxPlayers() - 1 || bulletsLeft==0) {
                gameGoing = false;
            }
        }
    }
    public void end() {
        if (playerAlive) {
            PlayerProfileChanger.changeMoney(player, player.getMoney() + (room.getBet() * room.getBulletsCount()));
            player.setMoney(player.getMoney() + (room.getBet() * room.getBulletsCount()));
        } else {
            player.setMoney(player.getMoney()-room.getBet());
        }
        ConnectionSingletone.terminateConnection();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent characterCreation = loader.load(Main.class.getResource("graphics/Menu.fxml").openStream());
            Scene scene = new Scene(characterCreation);
            Main.open((Stage) pane.getScene().getWindow(), scene);
            MenuController menuController = (MenuController) loader.getController();
            menuController.setPlayer(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
