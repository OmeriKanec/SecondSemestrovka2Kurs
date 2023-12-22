package src.models;

import java.util.UUID;

public class Room {
    private String name;
    private int maxPlayers;
    private int currentPlayers;
    private int bet;
    private int bulletsCount;
    private String deathsBeforeFinish;
    private UUID uuid;

    public Room(String name, int maxPlayers, int currentPlayers, int bet, int bulletsCount, String deathsBeforeFinish) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.bet = bet;
        this.bulletsCount = bulletsCount;
        this.deathsBeforeFinish = deathsBeforeFinish;
    }

    public Room(String name, int maxPlayers, int currentPlayers, int bet, int bulletsCount, String deathsBeforeFinish, UUID uuid) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.bet = bet;
        this.bulletsCount = bulletsCount;
        this.deathsBeforeFinish = deathsBeforeFinish;
        this.uuid = uuid;
    }

    public String getName() {
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

    public UUID getUuid() {
        return uuid;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    @Override
    public String toString() {
        return (name + " bet: " + bet + " players: " + currentPlayers + "/" + maxPlayers + " bullets in revolver: " + bulletsCount + " " + deathsBeforeFinish);
    }
}
