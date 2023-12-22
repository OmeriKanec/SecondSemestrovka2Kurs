package src.helpers;

public class ConnectionSingletone {
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null){
            connection = new Connection();
        }
        return connection;
    }
    public static void terminateConnection() {
        connection = null;
    }
}
