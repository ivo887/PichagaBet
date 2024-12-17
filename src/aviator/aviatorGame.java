package aviator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AviatorGame extends JPanel {
    private int totalMoney;
    private int currentBet;
    private double multiplier = 1.0;
    private boolean gameRunning = false;
    private Timer gameTimer;
    private JLabel totalMoneyLabel;
    private JLabel multiplierLabel;
    private JTextField betField;
    private JButton startGameButton;
    private JButton cashOutButton;

    private void initComponents() {
        totalMoneyLabel = new JLabel("Money: $" + totalMoney);
        multiplierLabel = new JLabel("Multiplier: 1.0x");
        betField = new JTextField(5);
        startGameButton = new JButton("Start Game");
        cashOutButton = new JButton("Cash Out");
        cashOutButton.setEnabled(false); // Disable until game starts

        JPanel bettingPanel = new JPanel();
        bettingPanel.add(new JLabel("Place your bet: "));
        bettingPanel.add(betField);
        bettingPanel.add(startGameButton);
        bettingPanel.add(cashOutButton);

        JPanel displayPanel = new JPanel();
        displayPanel.add(totalMoneyLabel);
        displayPanel.add(multiplierLabel);

        add(bettingPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        cashOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cashOut();
            }
        });
    }

    private void startGame() {
        try {
            currentBet = Integer.parseInt(betField.getText());
            if (currentBet > totalMoney) {
                JOptionPane.showMessageDialog(this, "You don't have enough money!");
                return;
            } else if (currentBet <= 0) {
                JOptionPane.showMessageDialog(this, "Bet must be greater than zero!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid bet amount!");
            return;
        }

        totalMoney -= currentBet;
        totalMoneyLabel.setText("Money: $" + totalMoney);
        startGameButton.setEnabled(false);
        betField.setEnabled(false);
        cashOutButton.setEnabled(true);

        gameRunning = true;
        multiplier = 1.0;
        gameTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameRunning) {
                    multiplier += 0.1; // Increase multiplier over time
                    multiplierLabel.setText(String.format("Multiplier: %.1fx", multiplier));
                    if (multiplier >= 10.0) { // Simulate a crash
                        endGame();
                    }
                }
            }
        });
        gameTimer.start();
    }

    private void cashOut() {
        if (!gameRunning) return;

        gameRunning = false;
        gameTimer.stop();
        double winnings = currentBet * multiplier;
        totalMoney += winnings;
        totalMoneyLabel.setText("Money: $" + totalMoney);
        JOptionPane.showMessageDialog(this, "You cashed out at " + String.format("%.1fx", multiplier) + ". You won $" + winnings);
        resetGame();
    }

    private void endGame() {
        gameRunning = false;
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "The plane flew away! You lost your bet.");
        resetGame();
    }

    private void resetGame() {
        startGameButton.setEnabled(true);
        betField.setEnabled(true);
        cashOutButton.setEnabled(false);
        betField.setText("");
        multiplierLabel.setText("Multiplier: 1.0x");
    }
}
