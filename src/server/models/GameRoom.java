package src.server.models;

import src.server.UserConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameRoom extends Thread {
    private String name;
    private int maxPlayers;
    private int currentPlayers;
    private int bet;
    private int bulletsCount;
    private String deathsBeforeFinish;
    List<UserConnection> connections;
    private UUID uuid;

    public GameRoom(String name, int maxPlayers, int currentPlayers, int bet, int bulletsCount, String deathsBeforeFinish, UUID uuid) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.bet = bet;
        this.bulletsCount = bulletsCount;
        this.deathsBeforeFinish = deathsBeforeFinish;
        this.connections = new ArrayList<>();
        this.uuid = uuid;
    }

    public GameRoom(String name, int maxPlayers, int currentPlayers, int bet, int bulletsCount, String deathsBeforeFinish) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.bet = bet;
        this.bulletsCount = bulletsCount;
        this.deathsBeforeFinish = deathsBeforeFinish;
        this.connections = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }


    @Override
    public void run() {

    }

    public String getGameName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public int getBet() {
        return bet;
    }

    public int getBulletsCount() {
        return bulletsCount;
    }

    public String getDeathsBeforeFinish() {
        return deathsBeforeFinish;
    }

    public List<UserConnection> getConnections() {
        return connections;
    }

    public UUID getUuid() {
        return uuid;
    }
    public void addUserConnection(UserConnection userConnection){
        if (connections.size() <= maxPlayers){
            connections.add(userConnection);
        }
    }
}
