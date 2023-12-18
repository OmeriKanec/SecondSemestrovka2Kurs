package src.server;

import src.server.Callbacks.AddRoomCallback;
import src.server.Callbacks.AddUserToRoomCallback;
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

    public UserConnection(Socket socket, AddRoomCallback addRoomCallback, AddUserToRoomCallback addUserToRoomCallback) {
        this.socket = socket;
        this.addRoomCallback = addRoomCallback;
        this.addUserToRoomCallback = addUserToRoomCallback;
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.printWriter = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                String[] messsage = bufferedReader.readLine().split(",");
                if (messsage[0].equals("newRoom")){
                    GameRoom room = new GameRoom(messsage[1], Integer.parseInt(messsage[2]), Integer.parseInt(messsage[3]), Integer.parseInt(messsage[4]),
                            Integer.parseInt(messsage[5]),Integer.parseInt(messsage[6]));
                    room.addUserConnection(this);
                    this.addRoomCallback.AddRoom(room);
                } else if (messsage[0].equals("join")) {
                    UUID uuid = UUID.fromString(messsage[1]);
                    this.addUserToRoomCallback.addUserToRoom(uuid, this);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendGameRooms(List<GameRoom> rooms) {
        for (GameRoom room: rooms) {
            printWriter.println(room.getGameName() + "," + room.getMaxPlayers() + "," + room.getCurrentPlayers() + "," + 
                    room.getBet() + "," + room.getBulletsCount() + "," + room.getDeathsBeforeFinish() + room.getUuid());
        }
        printWriter.println("$");
    }

    public void sendMessageToClient(String message) {
        //
    }
}
