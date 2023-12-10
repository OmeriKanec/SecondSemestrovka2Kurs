package src.helpers;

import src.models.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerProfileChanger {
    private static final String PATHTOFILEWITHID = "id.txt";
    private static final String PATHTOUSERPROFILESDIRECTORY = "UserProfiles";

    private static int getIdForNewAccount() {
        File file = new File(PATHTOFILEWITHID);
        if (!file.exists()) {
            try {
                file.createNewFile();
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
                return 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            int id = Integer.parseInt(bufferedReader.readLine());
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("");
            printWriter.println(id+1);
            printWriter.flush();
            printWriter.close();
            bufferedReader.close();
            return id;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createAccount(Player p){
        int id = getIdForNewAccount();
        String fileName = p.getUserName()+id;
        File file = new File(PATHTOUSERPROFILESDIRECTORY+ "/" + fileName);
        File directory = new File(PATHTOUSERPROFILESDIRECTORY);
        if (!directory.exists()){
            directory.mkdirs();
        }
        try {
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(id + "," + p.getUserName() + "," + p.getMoney());
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Player> getAllAccounts() {
        File folder = new File(PATHTOUSERPROFILESDIRECTORY);
        List<Player> players = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String[] characterInfo = bufferedReader.readLine().split(",");
                    Player p = new Player(characterInfo[1], Integer.parseInt(characterInfo[2]));
                    players.add(p);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return players;
    }

    public static Player getPlayer(String username, int money) {
        File folder = new File(PATHTOUSERPROFILESDIRECTORY);
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String[] characterInfo = bufferedReader.readLine().split(",");
                    if (characterInfo[1].equals(username) && Integer.parseInt(characterInfo[2]) == money) {
                        return new Player(Integer.parseInt(characterInfo[0]), characterInfo[1], Integer.parseInt(characterInfo[2]));
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static boolean changeMoney(Player p, int newMoney) {
        File folder = new File(PATHTOUSERPROFILESDIRECTORY);
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String[] characterInfo = bufferedReader.readLine().split(",");
                    if (characterInfo[1].equals(p.getUserName()) && Integer.parseInt(characterInfo[2]) == p.getMoney()) {
                        PrintWriter printWriter = new PrintWriter(file);
                        printWriter.print("");
                        printWriter.println(characterInfo[0]+ "," + characterInfo[1] + "," + newMoney);
                        printWriter.flush();
                        printWriter.close();
                        return true;
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println(getAllAccounts().get(1));
//    }
}
