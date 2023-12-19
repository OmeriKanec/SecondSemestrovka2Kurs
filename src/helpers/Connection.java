package src.helpers;

import src.models.Room;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Connection {
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    public Connection() {
        try {
            socket = new Socket("localhost", 1);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Not able to connect");
        }
    }
    public List<Room> getRooms() {
        try {
            printWriter.println(1);
            List<Room> rooms = new ArrayList<>();
            while (!bufferedReader.readLine().equals("$")){
                rooms.add(this.stringToRoom(bufferedReader.readLine()));
            }
            return rooms;
        } catch (IOException e) {
            throw new RuntimeException("Not able to get rooms");
        }
    }

    private Room stringToRoom(String serverResponse) {
        String[] resp = serverResponse.split(",");
        Room room = new Room(resp[0], Integer.parseInt(resp[1]), Integer.parseInt(resp[2]), Integer.parseInt(resp[3]),
                Integer.parseInt(resp[4]), Integer.parseInt(resp[5]), UUID.fromString(resp[6]));
        return room;
    }
    public void sendNewRoomToServer(Room room) {
        printWriter.println("newRoom" + "," + room.getName() + "," + room.getMaxPlayers() + "," + room.getCurrentPlayers() + "," +
                room.getBet() + "," + room.getBulletsCount() + "," + room.getDeathsBeforeFinish());
    }
}
