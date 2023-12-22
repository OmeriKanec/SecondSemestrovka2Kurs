package src.server;

import src.server.Callbacks.AddRoomCallback;
import src.server.models.GameRoom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread {

    private List<GameRoom> rooms;
    private List<UserConnection> connections;

    public Server() {
        this.rooms = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.addNewRoom(new GameRoom("fwfwf", 2,  100, 2, "До первой смерти"));
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1);
            while (true) {
                Socket socket = serverSocket.accept();
                UserConnection userConnection = new UserConnection(socket, this::addNewRoom, this::addUserToRoom, this::getRooms);
                userConnection.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addNewRoom(GameRoom room){
        room.setTerminateCallback(this::terminateRoom);
        rooms.add(room);
    }
    public boolean addUserToRoom(UUID uuid, UserConnection userConnection) {
        for (GameRoom room: rooms) {
            if (room.getUuid().equals(uuid)) {
                if (room.addUserConnection(userConnection)){
                    connections.remove(userConnection);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    public List<GameRoom> getRooms(){
        return this.rooms;
    }
    public void terminateRoom(UUID uuid) {
        for (GameRoom room : rooms) {
            if (room.getUuid().equals(uuid)) {
                rooms.remove(room);
            }
        }
    }

}
