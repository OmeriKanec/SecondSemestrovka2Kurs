package src.models;


import src.helpers.PlayerProfileChanger;

public class Player {
    private int id;
    private String userName;
    private int money;

    public Player(int id, String userName, int money) {
        this.id = id;
        this.userName = userName;
        this.money = money;
    }

    public Player(String userName, int money) {
        this.userName = userName;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return userName + ", money: " + money;
    }

    public void changeMoneyTo(int newMoney) {
        PlayerProfileChanger.changeMoney(this, newMoney);
        this.money = newMoney;
    }
}
