package aviator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class aviatorGame extends JPanel {
    private double multiplier = 1.0;
    private double crashPoint;
    private boolean isRunning = false;
    private Timer timer;
    private Timer planeTimer;
    private int totalMoney;
    private JTextField betField;
    private JLabel multiplierLabel;
    private JLabel moneyLabel;
    private JButton startButton;
    private JButton cashOutButton;
    private int currentBet;
    private boolean hasCashedOut = false;
    private JLabel planeLabel;
    private int planeX = 0;

    public aviatorGame(int startingMoney) {
        this.totalMoney = startingMoney;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        moneyLabel = new JLabel("Money: $" + totalMoney);
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(moneyLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(null);
        centerPanel.setBackground(Color.DARK_GRAY);

        multiplierLabel = new JLabel("Multiplier: x1.00");
        multiplierLabel.setForeground(Color.WHITE);
        multiplierLabel.setFont(new Font("Arial", Font.BOLD, 36));
        multiplierLabel.setBounds(10, 10, 400, 50);
        centerPanel.add(multiplierLabel);


        ImageIcon planeIcon = new ImageIcon("src/plane/plane.png");
        planeLabel = new JLabel(planeIcon);
        planeLabel.setBounds(planeX, 100, planeIcon.getIconWidth(), planeIcon.getIconHeight());
        centerPanel.add(planeLabel);

        add(centerPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel betLabel = new JLabel("Bet Amount:");
        betLabel.setForeground(Color.WHITE);
        betLabel.setHorizontalAlignment(SwingConstants.CENTER);
        betField = new JTextField();
        bottomPanel.add(betLabel);
        bottomPanel.add(betField);

        startButton = new JButton("Start");
        cashOutButton = new JButton("Cash Out");
        cashOutButton.setEnabled(false);
        bottomPanel.add(startButton);
        bottomPanel.add(cashOutButton);
        add(bottomPanel, BorderLayout.SOUTH);


        startButton.addActionListener(e -> startGame());
        cashOutButton.addActionListener(e -> cashOut());
    }

    private void startGame() {
        if (isRunning) return;
        try {
            currentBet = Integer.parseInt(betField.getText());
            if (currentBet <= 0 || currentBet > totalMoney) {
                JOptionPane.showMessageDialog(this, "Invalid bet amount!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid bet amount!");
            return;
        }


        totalMoney -= currentBet;
        updateMoneyDisplay();


        isRunning = true;
        hasCashedOut = false;
        multiplier = 1.0;
        crashPoint = generateCrashPoint();
        startButton.setEnabled(false);
        cashOutButton.setEnabled(true);


        planeX = 0;
        planeLabel.setBounds(planeX, 100, planeLabel.getWidth(), planeLabel.getHeight());


        timer = new Timer(100, e -> {
            if (multiplier >= crashPoint) {
                crash();
            } else {
                multiplier += 0.1;
                updateMultiplierDisplay();
            }
        });
        timer.start();


        startPlaneAnimation();
    }

    private void cashOut() {
        if (!isRunning || hasCashedOut) return;
        hasCashedOut = true;


        int winnings = (int) (currentBet * multiplier);
        totalMoney += winnings;
        updateMoneyDisplay();
        JOptionPane.showMessageDialog(this, "You cashed out at x" + String.format("%.2f", multiplier) + "! You won $" + winnings);
        endGame();
    }

    private void crash() {
        timer.stop();
        planeTimer.stop();
        JOptionPane.showMessageDialog(this, "Game over! The multiplier crashed at x" + String.format("%.2f", crashPoint));
        endGame();
    }

    private void endGame() {
        isRunning = false;
        startButton.setEnabled(true);
        cashOutButton.setEnabled(false);
        updateMultiplierDisplay();
    }

    private double generateCrashPoint() {
        Random rand = new Random();
        return 1.5 + rand.nextDouble() * 5;
    }

    private void updateMultiplierDisplay() {
        multiplierLabel.setText("Multiplier: x" + String.format("%.2f", multiplier));
    }

    private void updateMoneyDisplay() {
        moneyLabel.setText("Money: $" + totalMoney);
    }

    private void startPlaneAnimation() {
        planeTimer = new Timer(50, e -> {
            planeX += 5;
            planeLabel.setBounds(planeX, 100, planeLabel.getWidth(), planeLabel.getHeight());

            if (planeX > getWidth()) {
                planeTimer.stop();
            }
        });
        planeTimer.start();
    }
}

