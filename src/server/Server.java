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
        //this.addNewRoom(new GameRoom("fwfwf", 2, 1, 100, 2, "До первой смерти"));
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
    
    public void sendToOthersInRoom(UUID uuid, String message) {

    }

    public void removeUnusedRoom() {

    }
    public void addNewRoom(GameRoom room){
        rooms.add(room);
    }
    public void addUserToRoom(UUID uuid, UserConnection userConnection){
        for (GameRoom room: rooms) {
            if (room.getUuid() == uuid){
                room.addUserConnection(userConnection);
                break;
            }
        }
    }
    public List<GameRoom> getRooms(){
        return this.rooms;
    }

}
