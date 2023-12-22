package src.server;

import src.server.Callbacks.*;
import src.server.models.GameRoom;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

public class UserConnection extends Thread {

    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private AddRoomCallback addRoomCallback;
    private AddUserToRoomCallback addUserToRoomCallback;
    private GetRoomsCallback getRoomsCallback;
    private GetCurrentPlayersCallback getCurrentPlayersCallback;
    private SpinCallback spinCallback;
    private ActivePlayerShotCallback activePlayerShotCallback;

    public UserConnection(Socket socket, AddRoomCallback addRoomCallback, AddUserToRoomCallback addUserToRoomCallback, GetRoomsCallback getRoomsCallback) {
        this.socket = socket;
        this.addRoomCallback = addRoomCallback;
        this.addUserToRoomCallback = addUserToRoomCallback;
        this.getRoomsCallback = getRoomsCallback;
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.printWriter = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
            try {
                boolean inRoom = false;
                while (!inRoom) {
                    String[] messsage = bufferedReader.readLine().split(",");
                    System.out.println(messsage[0]);
                    if (messsage[0].equals("roomListRequest")) {
                        sendGameRooms(getRoomsCallback.getRooms());
                    } else if (messsage[0].equals("newRoom")) {
                        GameRoom room = new GameRoom(messsage[1], Integer.parseInt(messsage[2]), Integer.parseInt(messsage[3]),
                                Integer.parseInt(messsage[4]), messsage[5]);
                        room.addUserConnection(this);
                        this.addRoomCallback.AddRoom(room);
                        inRoom = true;
                    } else if (messsage[0].equals("join")) {
                        UUID uuid = UUID.fromString(messsage[1]);
                        System.out.println(uuid);
                        if (this.addUserToRoomCallback.addUserToRoom(uuid, this)){
                            inRoom = true;
                            System.out.println("sending true");
                            printWriter.println("true");
                            printWriter.flush();
                        } else {
                            printWriter.println("false");
                            printWriter.flush();
                        }
                    }
                }
                while (inRoom){
                    String message = bufferedReader.readLine();
                    if (message.equals("getCurrentPlayers")){
                        System.out.println("sendingCurrentPlayers");
                        printWriter.println(getCurrentPlayersCallback.getCurrentPlayers());
                        printWriter.flush();
                    } else if (message.equals("spin")) {
                        spinCallback.spin();
                    } else if (message.equals("shoot")) {
                        activePlayerShotCallback.setActivePlayerShot();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public void sendGameRooms(List<GameRoom> rooms) {
        for (GameRoom room: rooms) {
            printWriter.println(room.getGameName() + "," + room.getMaxPlayers() + "," + room.getCurrentPlayers() + "," +
                    room.getBet() + "," + room.getBulletsCount() + "," + room.getDeathsBeforeFinish() + "," + room.getUuid());
            printWriter.flush();
        }
        printWriter.println("$");
        printWriter.flush();
    }

    public void sendCurrentPlayers() {
        printWriter.println("CurrentPlayers:" + getCurrentPlayersCallback.getCurrentPlayers());
        printWriter.flush();
    }
    public void setGetCurrentPlayersCallback(GetCurrentPlayersCallback getCurrentPlayersCallback) {
        this.getCurrentPlayersCallback = getCurrentPlayersCallback;
    }

    public void setSpinCallback(SpinCallback spinCallback) {
        this.spinCallback = spinCallback;
    }

    public void setActivePlayerShotCallback(ActivePlayerShotCallback activePlayerShotCallback) {
        this.activePlayerShotCallback = activePlayerShotCallback;
    }

    public void sendSpin() {
        printWriter.println("spin");
        printWriter.flush();
    }
    public void sendShoot(boolean shot, int activePlayer){
        String state;
        if (shot){
            state = "dead";
        } else {
            state = "alive";
        }
        printWriter.println(state+":"+activePlayer);
        printWriter.flush();
    }
}
