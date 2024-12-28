package aviator;

import Money.MoneyManager;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.text.DecimalFormat;

public class Aviator extends JPanel {
    private MoneyManager moneyManager;
    private Timer timer;
    private DecimalFormat df_obj = new DecimalFormat("#.##");
    private boolean gameRunning = false;
    private double currentMultiplier = 1.0;
    private double betAmount = 0.0;

    public Aviator() {
        moneyManager = MoneyManager.getInstance();
        initComponents();
        this.setVisible(true);
        Money.setText("Money: $" + moneyManager.getTotalMoney());
        Multyplier.setText("Multiplier: 0.00");
        Bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeBet();
            }
        });
        Out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cashOut();
            }
        });
    }

    private void placeBet() {
        if (gameRunning) {
            JOptionPane.showMessageDialog(this, "Game is already running!");
            return;
        }
        try {
            betAmount = Double.parseDouble(Amount.getText());
            if (betAmount > moneyManager.getTotalMoney()) {
                JOptionPane.showMessageDialog(this, "Insufficient funds!");
                return;
            }
            moneyManager.setTotalMoney(moneyManager.getTotalMoney() - betAmount);
            Money.setText("Money: $" + moneyManager.getTotalMoney());
            startGame();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid bet amount!");
        }
    }

    private void startGame() {
        gameRunning = true;
        currentMultiplier = 1.0;
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMultiplier();
            }
        });
        timer.start();
    }

    private void updateMultiplier() {
        currentMultiplier += 0.1;
        Multyplier.setText("Multiplier: " + df_obj.format(currentMultiplier));
        if (new Random().nextDouble() < 0.01) { // 1% chance to crash
            timer.stop();
            gameRunning = false;
            JOptionPane.showMessageDialog(this, "Plane crashed! You lost your bet.");
        }
    }

    private void cashOut() {
        if (!gameRunning) {
            JOptionPane.showMessageDialog(this, "No game running!");
            return;
        }
        timer.stop();
        gameRunning = false;
        double winnings = betAmount * currentMultiplier;
        moneyManager.setTotalMoney(moneyManager.getTotalMoney() + winnings);
        Money.setText("Money: $" + moneyManager.getTotalMoney());
        JOptionPane.showMessageDialog(this, "You cashed out at " + df_obj.format(currentMultiplier) + "x and won $" + df_obj.format(winnings) + "!");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
        Amount = new JTextField();
        Animation = new JPanel();
        Bet = new JButton();
        Out = new JButton();
        Money = new JLabel();
        Multyplier = new JLabel();

        //======== this ========
        setBackground(Color.black);

        //---- Amount ----
        Amount.setBackground(new Color(0x333333));
        Amount.setForeground(Color.white);

        //======== Animation ========
        {

            GroupLayout AnimationLayout = new GroupLayout(Animation);
            Animation.setLayout(AnimationLayout);
            AnimationLayout.setHorizontalGroup(
                AnimationLayout.createParallelGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
            );
            AnimationLayout.setVerticalGroup(
                AnimationLayout.createParallelGroup()
                    .addGap(0, 348, Short.MAX_VALUE)
            );
        }

        //---- Bet ----
        Bet.setText("Bet");
        Bet.setBackground(new Color(0x333333));
        Bet.setForeground(Color.white);
        Bet.setActionCommand("Bet");

        //---- Out ----
        Out.setText("Cash out");
        Out.setBackground(new Color(0x333333));
        Out.setForeground(Color.white);
        Out.setActionCommand("Cash Out");

        //---- Money ----
        Money.setText("text");
        Money.setBackground(Color.black);
        Money.setForeground(Color.white);
        Money.setFont(new Font("Inter", Font.PLAIN, 14));

        //---- Multyplier ----
        Multyplier.setForeground(Color.white);
        Multyplier.setBackground(Color.black);
        Multyplier.setFont(new Font("Inter", Font.PLAIN, 14));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(456, 456, 456)
                            .addComponent(Multyplier, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(Money, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addComponent(Animation, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Amount, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(Bet, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(Out, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                            .addGap(1, 1, 1)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(Multyplier, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Money, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(Animation, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(Out, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addComponent(Bet, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addComponent(Amount, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                    .addContainerGap(10, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
    private JTextField Amount;
    private JPanel Animation;
    private JButton Bet;
    private JButton Out;
    private JLabel Money;
    private JLabel Multyplier;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}