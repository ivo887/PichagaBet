package history;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HistoryManager {
    public HistoryManager() {

    }

    public static void saveHistory(boolean isResultWin, double currentMoney, String gamePlayed) {
        try (FileWriter writer = new FileWriter("src/history/history.txt", true)) {
            writer.write(String.format("Game: %s | Result: %s | Money: $%.2f\n",
                    gamePlayed, isResultWin ? "Win" : "Loss", currentMoney));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


