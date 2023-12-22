package src.server.models;

import src.server.Callbacks.TerminateCallback;
import src.server.UserConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameRoom extends Thread {
    private String name;
    private int maxPlayers;
    private int bet;
    private int bulletsCount;
    private String deathsBeforeFinish;
    List<UserConnection> connections;
    private UUID uuid;
    private int activePlayer;
    private boolean gameGoing;
    private boolean activePlayerShot;
    private boolean needToSpin;
    private int[] bullets = new int[6];
    private int deadCount;
    private int[] deadOrAlive;
    private TerminateCallback terminateCallback;

    public GameRoom(String name, int maxPlayers, int bet, int bulletsCount, String deathsBeforeFinish, UUID uuid) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.bet = bet;
        this.bulletsCount = bulletsCount;
        this.deathsBeforeFinish = deathsBeforeFinish;
        this.connections = new ArrayList<>();
        this.uuid = uuid;
    }

    public GameRoom(String name, int maxPlayers, int bet, int bulletsCount, String deathsBeforeFinish) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.bet = bet;
        this.bulletsCount = bulletsCount;
        this.deathsBeforeFinish = deathsBeforeFinish;
        this.connections = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }


    @Override
    public void run() {
        deadCount = 0;
        gameGoing = true;
        activePlayerShot = false;
        activePlayer = 0;
        deadOrAlive = new int[maxPlayers];
        for (int i = 0; i < deadOrAlive.length; i++){
            deadOrAlive[i] = 1;
        }
        this.inserBullets();
//        while (gameGoing) {
//            if (needToSpin) {
//                needToSpin = false;
//                spin();
//                sendSpin();
//            }
//            if (activePlayerShot) {
//                activePlayerShot = false;
//                boolean dead = false;
//                System.out.println("shooting");
//                if (bullets[0] == 1){
//                    dead = true;
//                    nextSlot();
//                    deadOrAlive[activePlayer] = 0;
//                    bulletsCount--;
//                    deadCount++;
//                }
//                sendShot(dead, activePlayer);
//                nextTurn();
//            }
//            if (deathsBeforeFinish.equals("До первой смерти")) {
//                if (deadCount > 0) {
//                    gameGoing = false;
//                }
//            } else {
//                if (deadCount == maxPlayers - 1) {
//                    gameGoing = false;
//                }
//            }
//        }
        //send game over
    }

    private void nextTurn() {
        int inc = 1;
        while (deadOrAlive[(activePlayer+inc)%deadOrAlive.length] == 0){
            inc++;
        }
        activePlayer = (activePlayer + inc)%deadOrAlive.length;
    }
    private void nextSlot(){
        int temp = bullets[0];
        for (int i = 0; i<5; i++){
            bullets[i] = bullets[i+1];
        }
        bullets[5] = temp;
        for (int i = 0; i<6; i++){
            System.out.println(i+":"+bullets[i]);
        }
    }

    public String getGameName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getCurrentPlayers() {
        return connections.size();
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
    public boolean addUserConnection(UserConnection userConnection){
        if (connections.size() < maxPlayers){
            connections.add(userConnection);
            userConnection.setGetCurrentPlayersCallback(this::getCurrentPlayers);
            userConnection.setSpinCallback(this::setNeedToSpin);
            userConnection.setActivePlayerShotCallback(this::setActivePlayerShot);
            for (UserConnection userCon: connections) {
                if (!userCon.equals(userConnection)){
                    userCon.sendCurrentPlayers();
                }
            }
            if (connections.size() == maxPlayers) {
                this.start();
            }
            return true;
        } else {
            return false;
        }
    }
    public void inserBullets() {
        for (int i =0; i < 6; i++){
            bullets[i] = 0;
        }
        int inserted = 0;
        Random random = new Random();
        while (inserted != bulletsCount){
            for (int i = 0; i < 6; i++){
                if (bullets[i] != 1 && inserted != bulletsCount){
                    if (random.nextInt(10) > 5){
                        bullets[i] = 1;
                        inserted++;
                    }
                }
            }
        }
    }
    public void spin() {
        Random random = new Random();
        int change = random.nextInt(6)+1;
        if (change != 6){
            int[] temp = new int[6];
            for (int i = 0; i<6; i++){
                temp[i] = bullets[i];

            }
            for (int i = 0; i<6; i++){
                bullets[(i+change)%6] = temp[i];
            }
        }
    }
    public void sendSpin() {
        for (UserConnection userCon: connections) {
            userCon.sendSpin();
        }
    }
    public void sendShot(boolean dead, int act) {
        for (UserConnection userCon: connections) {
            userCon.sendShoot(dead, act);
        }
    }

    public void setNeedToSpin() {
        spin();
               sendSpin();
    }

    public void setActivePlayerShot() {
                activePlayerShot = false;
                boolean dead = false;
                System.out.println("shooting");
                if (bullets[0] == 1){
                    dead = true;
                    bullets[0] = 0;
                    deadOrAlive[activePlayer] = 0;
                    bulletsCount--;
                    deadCount++;
                }
        nextSlot();
                sendShot(dead, activePlayer);
                nextTurn();
            if (deathsBeforeFinish.equals("До первой смерти")) {
                if (deadCount > 0 || bulletsCount == 0) {
                    this.terminate();
                }
            } else {
                if (deadCount == maxPlayers - 1 || bulletsCount == 0) {
                    this.terminate();
                }
            }
    }
    private void terminate() {
        for (UserConnection conn: connections) {
            conn = null;
        }
        terminateCallback.terminate(this.uuid);
    }

    public void setTerminateCallback(TerminateCallback terminateCallback) {
        this.terminateCallback = terminateCallback;
    }
}
