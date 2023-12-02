package models;


public class Player {
    private long id;
    private String userName;
    private String password;
    private int money;
    private int avatarFaceId;
    private int avatarSkinId;

    public Player(long id, String userName, String password, int money, int avatarFaceId, int avatarSkinId) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.money = money;
        this.avatarFaceId = avatarFaceId;
        this.avatarSkinId = avatarSkinId;
    }

    public Player(String userName, String password, int avatarFaceId, int avatarSkinId) {
        this.userName = userName;
        this.password = password;
        this.avatarFaceId = avatarFaceId;
        this.avatarSkinId = avatarSkinId;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getAvatarFaceId() {
        return avatarFaceId;
    }

    public void setAvatarFaceId(int avatarFaceId) {
        this.avatarFaceId = avatarFaceId;
    }

    public int getAvatarSkinId() {
        return avatarSkinId;
    }

    public void setAvatarSkinId(int avatarSkinId) {
        this.avatarSkinId = avatarSkinId;
    }
}
