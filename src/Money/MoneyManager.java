package Money;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MoneyManager {
    private static MoneyManager instance;
    private int totalMoney;

    private MoneyManager() {
        loadMoney();
    }

    public static synchronized MoneyManager getInstance() {
        if (instance == null) {
            instance = new MoneyManager();
        }
        return instance;
    }

    private void loadMoney() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Money/money.txt"))) {
            totalMoney = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int getTotalMoney() {
        return totalMoney;
    }

    public synchronized void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
        saveMoney();
    }

    private void saveMoney() {
        try (FileWriter writer = new FileWriter("src/Money/money.txt")) {
            writer.write(String.valueOf(totalMoney));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}