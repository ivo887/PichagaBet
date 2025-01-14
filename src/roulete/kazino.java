package roulete;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import Money.MoneyManager;
import history.HistoryManager;

public class kazino extends JPanel {
    private String[] bet1 = {"Straight Up (1 number)", "Split (2 numbers)", "Street (3 numbers)", "Corner (4 numbers)", "Color(Black or Red)", "Odd or Even", "Lower or Higher"};
    private String[] bet2 = {"Odd", "Even"};
    private String[] bet3 = {"Black", "Red", "Green"};
    private String[] bet4 = {"Higher", "Lower"};
    private RoulettePanel roulettePanel;
    private Timer timer;
    private double angle = 0;
    private final double diff = 360 / 74.0;
    private final MoneyManager moneyManager = MoneyManager.getInstance();
    private final HistoryManager historyManager = new HistoryManager();

    public kazino() {
        initComponents();
        comboBox1.setForeground(Color.WHITE);
        textField1.setCaretColor(Color.white);
        numbers.setCaretColor(Color.white);
        roulettePanel = new RoulettePanel();
        panel2.add(roulettePanel);
        roulettePanel.setBackground(Color.darkGray);
        for (String bet : bet1) {
            comboBox1.addItem(bet);
        }

        updateMoneyDisplay();

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = comboBox1.getSelectedItem().toString();
                Colors1.removeAllItems();
                switch (selectedItem) {
                    case "Color(Black or Red)":
                        for (String color : bet3) {
                            Colors1.addItem(color);
                        }
                        break;
                    case "Odd or Even":
                        for (String option : bet2) {
                            Colors1.addItem(option);
                        }
                        break;
                    case "Lower or Higher":
                        for (String option : bet4) {
                            Colors1.addItem(option);
                        }
                        break;
                }
            }
        });

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int bet = Integer.parseInt(textField1.getText());
                    if (moneyManager.getTotalMoney() < bet) {
                        JOptionPane.showMessageDialog(null, "You don't have enough money to place this bet.");
                        return;
                    }
                    String betNumbers = numbers.getText().trim();
                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() - bet);
                    button1.setEnabled(false);
                    Random rand = new Random();
                    int rand_int1 = rand.nextInt(37);
                    angle = 0;
                    timer = new Timer(10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            double targetAngle = rand_int1 * (360.0 / 37) + 720;
                            if (angle < targetAngle) {
                                angle += diff;
                                if (angle > targetAngle) {
                                    angle = targetAngle;
                                }
                            } else {
                                timer.stop();
                                new ProgressTask(rand_int1, bet, (String) comboBox1.getSelectedItem(), betNumbers).execute();
                            }
                            roulettePanel.repaint();
                        }
                    });

                    timer.start();

                } catch (NumberFormatException ex) {
                    // Handle exception
                }
            }
        });
    }

    private class RoulettePanel extends JPanel {
        private final int NUM_SEGMENTS = 37;
        private final Color[] COLORS = {
                Color.green, Color.red, Color.black, Color.red, Color.black, Color.red, Color.black,
                Color.red, Color.black, Color.red, Color.black, Color.red, Color.black, Color.red,
                Color.black, Color.red, Color.black, Color.red, Color.black, Color.red, Color.black,
                Color.red, Color.black, Color.red, Color.black, Color.red, Color.black, Color.red,
                Color.black, Color.red, Color.black, Color.red, Color.black, Color.red, Color.black,
                Color.red, Color.black
        };

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(Color.darkGray);
            g2d.setColor(Color.white);
            g2d.fillRect(getWidth() / 2 + getHeight() / 2 + 2, getHeight() / 2 - getHeight() / 16, getHeight() / 64, getHeight() / 64);

            int diameter = Math.min(getWidth(), getHeight());
            int radius = diameter / 2;

            g2d.rotate(Math.toRadians(angle), getWidth() / 2, getHeight() / 2);

            for (int i = 0; i < NUM_SEGMENTS; i++) {
                g2d.setColor(COLORS[i]);
                double startAngle = (360.0 / NUM_SEGMENTS) * i;
                double arcAngle = (360.0 / NUM_SEGMENTS);
                g2d.fillArc(getWidth() / 2 - radius, getHeight() / 2 - radius, diameter, diameter, (int) startAngle, (int) arcAngle);
            }

            g2d.setColor(Color.black);
            g2d.drawArc(getWidth() / 2 - radius, getHeight() / 2 - radius, diameter, diameter, 0, 360);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 800);
        }
    }

    private void updateMoneyDisplay() {
        moneyLabel.setText("Money: $" + moneyManager.getTotalMoney());
    }

    private class ProgressTask extends SwingWorker<Void, Integer> {
        private final int rand;
        private final int bet;
        private final String betType;
        private final String betNumbers;

        public ProgressTask(int rand, int bet, String betType, String betNumbers) {
            this.rand = rand;
            this.bet = bet;
            this.betType = betType;
            this.betNumbers = betNumbers;
        }

        protected Void doInBackground() {
            for (int i = 0; i <= this.rand; ++i) {
                publish(i);
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void process(List<Integer> chunks) {
            for (int value : chunks) {
                button1.setText(Integer.toString(value));
            }
        }

        @Override
        protected void done() {
            button1.setEnabled(true);
            boolean win = false;

            String[] numbersArray = betNumbers.split(",");
            int payoutMultiplier = 0;
            for (String number : numbersArray) {
                try {
                    if (!betType.equals("Color(Black or Red)") && !betType.equals("Odd or Even") && !betType.equals("Lower or Higher")) {
                        int chosenNumber = Integer.parseInt(number.trim());
                        if (chosenNumber < 0 || chosenNumber > 36) {
                            JOptionPane.showMessageDialog(null, "Please enter valid numbers (0-36).");
                            return;
                        }

                        switch (betType) {
                            case "Straight Up (1 number)":
                                payoutMultiplier = 35;
                                if (rand == chosenNumber) {
                                    win = true;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                }
                                break;
                            case "Split (2 numbers)":
                                payoutMultiplier = 17;
                                if (rand == chosenNumber || (numbersArray.length == 2 &&
                                        (rand == Integer.parseInt(numbersArray[0].trim()) ||
                                                rand == Integer.parseInt(numbersArray[1].trim())))) {
                                    win = true;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                }
                                break;
                            case "Street (3 numbers)":
                                payoutMultiplier = 11;
                                if (numbersArray.length == 3 &&
                                        (rand == Integer.parseInt(numbersArray[0].trim()) ||
                                                rand == Integer.parseInt(numbersArray[1].trim()) ||
                                                rand == Integer.parseInt(numbersArray[2].trim()))) {
                                    win = true;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                }
                                break;
                            case "Corner (4 numbers)":
                                payoutMultiplier = 8;
                                if (numbersArray.length == 4 &&
                                        (rand == Integer.parseInt(numbersArray[0].trim()) ||
                                                rand == Integer.parseInt(numbersArray[1].trim()) ||
                                                rand == Integer.parseInt(numbersArray[2].trim()) ||
                                                rand == Integer.parseInt(numbersArray[3].trim()))) {
                                    win = true;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                }
                                break;
                        }
                    } else {
                        switch (betType) {
                            case "Color(Black or Red)":
                                if (Colors1.getSelectedItem().equals("Black") && rand % 2 == 0 && rand != 0) {
                                    win = true;
                                    payoutMultiplier = 2;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                } else if (Colors1.getSelectedItem().equals("Red") && rand % 2 != 0 && rand != 0) {
                                    win = true;
                                    payoutMultiplier = 2;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                } else if (Colors1.getSelectedItem().equals("Green") && rand == 0) {
                                    win = true;
                                    payoutMultiplier = 35;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                }
                                break;
                            case "Odd or Even":
                                if (Colors1.getSelectedItem().equals("Even") && rand % 2 == 0) {
                                    win = true;
                                    payoutMultiplier = 2;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                } else if (Colors1.getSelectedItem().equals("Odd") && rand % 2 != 0) {
                                    win = true;
                                    payoutMultiplier = 2;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                }
                                break;
                            case "Lower or Higher":
                                if (Colors1.getSelectedItem().equals("Higher") && rand > Integer.parseInt(numbersArray[0].trim())) {
                                    win = true;
                                    payoutMultiplier = 2;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                } else if (Colors1.getSelectedItem().equals("Lower") && rand > Integer.parseInt(numbersArray[0].trim())) {
                                    win = true;
                                    payoutMultiplier = 2;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                } else if (rand == Integer.parseInt(numbersArray[0].trim())) {
                                    win = true;
                                    payoutMultiplier = 1;
                                    moneyManager.setTotalMoney(moneyManager.getTotalMoney() + bet * payoutMultiplier);
                                }
                                break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    if (!betType.equals("Color(Black or Red)") && !betType.equals("Odd or Even") && !betType.equals("Lower or Higher")) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers (0-36).");
                    }
                    return;
                }
            }

            if (win) {
                JOptionPane.showMessageDialog(null, "You win!");
                historyManager.saveHistory(true, moneyManager.getTotalMoney(), "Roulete");
            } else {
                JOptionPane.showMessageDialog(null, "You lose your bet of $" + bet);
                historyManager.saveHistory(false, moneyManager.getTotalMoney(), "Roulete");
            }
            updateMoneyDisplay();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
        panel1 = new JPanel();
        textField1 = new JTextField();
        button1 = new JButton();
        moneyLabel = new JLabel();
        numbers = new JTextField();
        comboBox1 = new JComboBox();
        label1 = new JLabel();
        label2 = new JLabel();
        Colors1 = new JComboBox();
        panel2 = new JPanel();

        //======== panel1 ========
        {
            panel1.setBackground(Color.black);

            //---- textField1 ----
            textField1.setBackground(new Color(0x333333));
            textField1.setAlignmentX(1.0F);
            textField1.setForeground(Color.white);
            textField1.setDisabledTextColor(Color.white);
            textField1.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            textField1.setCaretColor(Color.white);

            //---- button1 ----
            button1.setBackground(new Color(0x333333));
            button1.setForeground(Color.white);
            button1.setText("BET");

            //---- moneyLabel ----
            moneyLabel.setBackground(Color.white);
            moneyLabel.setForeground(Color.white);
            moneyLabel.setFont(new Font("Inter", Font.ITALIC, 18));

            //---- numbers ----
            numbers.setForeground(Color.white);
            numbers.setBackground(new Color(0x333333));
            numbers.setCaretColor(Color.white);

            //---- comboBox1 ----
            comboBox1.setBackground(new Color(0x333333));
            comboBox1.setFont(comboBox1.getFont().deriveFont(comboBox1.getFont().getSize() - 2f));

            //---- label1 ----
            label1.setText("numbers:");
            label1.setForeground(Color.white);
            label1.setFont(label1.getFont().deriveFont(20f));

            //---- label2 ----
            label2.setText("amount:");
            label2.setForeground(Color.white);
            label2.setFont(label2.getFont().deriveFont(20f));

            //---- Colors1 ----
            Colors1.setBackground(new Color(0x333333));
            Colors1.setForeground(Color.white);

            //======== panel2 ========
            {
                panel2.setBackground(new Color(0x333333));
                panel2.setForeground(new Color(0x333333));
                panel2.setLayout(new GridLayout());
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap(544, Short.MAX_VALUE)
                        .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(Colors1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                                    .addComponent(numbers, GroupLayout.Alignment.LEADING)
                                    .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(60, 60, 60)
                                .addComponent(comboBox1, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(textField1)
                                    .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                                .addContainerGap())))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(panel2, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(numbers, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                    .addComponent(comboBox1, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label2, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                    .addComponent(button1, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Colors1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
            );
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
    public JPanel panel1;
    private JTextField textField1;
    private JButton button1;
    private JLabel moneyLabel;
    private JTextField numbers;
    private JComboBox comboBox1;
    private JLabel label1;
    private JLabel label2;
    private JComboBox Colors1;
    private JPanel panel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}