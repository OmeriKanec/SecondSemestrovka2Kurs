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
            printWriter.println("roomListRequest");
            printWriter.flush();
            List<Room> rooms = new ArrayList<>();
            String[] str;
            do {
                str = bufferedReader.readLine().split(",");
                if (!str[0].equals("$")) {
                    rooms.add(this.stringToRoom(str));
                }
            } while (!str[0].equals("$"));
            return rooms;
        } catch (IOException e) {
            throw new RuntimeException("Not able to get rooms");
        }
    }

    private Room stringToRoom(String[] resp) {
        Room room = new Room(resp[0], Integer.parseInt(resp[1]), Integer.parseInt(resp[2]), Integer.parseInt(resp[3]),
                Integer.parseInt(resp[4]), resp[5], UUID.fromString(resp[6]));
        return room;
    }
    public void sendNewRoomToServer(Room room) {
        printWriter.println("newRoom" + "," + room.getName() + "," + room.getMaxPlayers() + ","  +
                room.getBet() + "," + room.getBulletsCount() + "," + room.getDeathsBeforeFinish());
        printWriter.flush();
    }
    public boolean connectToRoom(UUID uuid) {
        printWriter.println("join" + "," + uuid);
        printWriter.flush();
        try {
            if (bufferedReader.readLine().equals("true")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int getCurrentPlayers() {
        printWriter.println("getCurrentPlayers");
        printWriter.flush();
        try {
            return Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int waitForServerToSendCurrentPlayers() {
        while (true) {
            String[] resp = new String[0];
            try {
                resp = bufferedReader.readLine().split(":");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (resp[0].equals("CurrentPlayers")){
                return Integer.parseInt(resp[1]);
            }
        }
    }
    public void sendCommandToSpin() {
        printWriter.println("spin");
        printWriter.flush();
    }
    public void sendCommandToShoot() {
        printWriter.println("shoot");
        printWriter.flush();
    }
    public String listenForStateChanges () {
        try {
            String[] message = bufferedReader.readLine().split(":");
            System.out.println(message[0]);
            if (message[0].equals("spin")){
                return "spin";
            } else if (message[0].equals("dead")) {
                return ("dead:"+message[1]);
            } else if (message[0].equals("alive")) {
                return "alive";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
