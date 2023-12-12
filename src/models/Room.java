package src.models;

public class Room {
    String name;
    int maxPlayers;
    int currentPlayers;
    int bet;
    int bulletsCount;
    int deathsBeforeFinish;

    public Room(String name, int maxPlayers, int currentPlayers, int bet, int bulletsCount, int deathsBeforeFinish) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.bet = bet;
        this.bulletsCount = bulletsCount;
        this.deathsBeforeFinish = deathsBeforeFinish;
    }
}
