package src.helpers;

import src.models.Room;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection {
    private Socket socket;
    public Connection() {
        try {
            socket = new Socket("localhost", 1);
        } catch (IOException e) {
            throw new RuntimeException("Not able to connect");
        }
    }
    public List<Room> getRooms() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
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

    private Room stringToRoom(String serverResponse){
        String[] resp = serverResponse.split(",");
        Room room = new Room(resp[0], Integer.parseInt(resp[1]), Integer.parseInt(resp[2]), Integer.parseInt(resp[3]),
                Integer.parseInt(resp[4]), Integer.parseInt(resp[5]));
        return room;
    }
}
