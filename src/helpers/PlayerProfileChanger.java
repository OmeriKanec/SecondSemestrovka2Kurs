package src.helpers;

import src.models.Player;

import java.io.*;

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

    public static void main(String[] args) {
        Player p = new Player("fff", 500);
        createAccount(p);
    }
}
