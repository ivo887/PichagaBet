package roulete;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;





public class kazino extends JFrame {
    private int totalMoney = 100; // Starting money// Combo box for betting options
    private String bet1[]={"Straight Up (1 number)","Split (2 numbers)", "Street (3 numbers)", "Corner (4 numbers)", "Color(Black or Red)", "Odd or Even", "Lower or Higher"};
    private String bet2[]={"1-18", "19-36"};
    private String bet3[]={"Black", "Red", "Green"};
    private RoulettePanel roulettePanel;
    private Timer timer;
    private int angle = 0;

    public kazino() {
        initComponents();
        JFrame frame = new JFrame("PichagaBET");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(this.panel1);
        frame.setVisible(true);
        comboBox1.setForeground(Color.white);
        textField1.setCaretColor(Color.white);
        textField2.setCaretColor(Color.white);
        roulettePanel = new RoulettePanel();
        panel2.add(roulettePanel);
        roulettePanel.setBackground(Color.darkGray);


        updateMoneyDisplay();

        this.button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int bet = Integer.parseInt(textField1.getText());
                    String betNumbers = textField2.getText().trim();
                    if (bet > totalMoney || bet <= 0) {
                        JOptionPane.showMessageDialog(frame, "Invalid bet amount!");
                        return;
                    }
                    totalMoney -= bet;
                    button1.setEnabled(false);
                    Random rand = new Random();
                    int rand_int1 = rand.nextInt(37);
                    new ProgressTask(rand_int1, bet, (String) comboBox1.getSelectedItem(), betNumbers).execute();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
            }
        });
    }
    private class RoulettePanel extends JPanel {
        private final int NUM_SEGMENTS = 37; // 36 numbers + 0
        private final Color[] COLORS = {
                Color.GREEN,Color.RED,
                Color.BLACK,Color.RED, Color.BLACK, Color.RED, Color.BLACK,
                Color.RED, Color.BLACK, Color.RED, Color.BLACK, Color.RED,
                Color.BLACK, Color.RED, Color.BLACK, Color.RED, Color.BLACK,
                Color.RED, Color.BLACK, Color.RED, Color.BLACK, Color.RED,
                Color.BLACK, Color.RED, Color.BLACK, Color.RED, Color.BLACK,
                Color.RED, Color.BLACK, Color.RED, Color.BLACK, Color.RED,
                Color.BLACK, Color.RED, Color.BLACK, Color.RED, Color.BLACK
        };

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(Color.DARK_GRAY);

            int diameter = Math.min(getWidth(), getHeight());
            int radius = diameter / 2;

            g2d.rotate(Math.toRadians(angle), getWidth() / 2, getHeight() / 2);

            for (int i = 0; i < NUM_SEGMENTS; i++) {
                g2d.setColor(COLORS[i]);
                double startAngle = (360.0 / NUM_SEGMENTS) * i;
                double arcAngle = (360.0 / NUM_SEGMENTS);
                g2d.fillArc(getWidth() / 2 - radius, getHeight() / 2 - radius, diameter, diameter, (int) startAngle, (int) arcAngle);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawArc(getWidth() / 2 - radius, getHeight() / 2 - radius, diameter, diameter, 0, 360);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 800);
        }
    }

    private void updateMoneyDisplay() {
        moneyLabel.setText("Money: $" + totalMoney);
    }

    private class ProgressTask extends SwingWorker<Void, Integer> {
        private int rand;
        private int bet;
        private String betType;
        private String betNumbers;


        public ProgressTask(int rand, int bet, String betType, String betNumbers) {
            this.rand = rand;
            this.bet = bet;
            this.betType = betType;
            this.betNumbers = betNumbers;
            angle = 0;
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (angle < 720+rand*360/37) {
                        angle += 4;
                    }
                    roulettePanel.repaint();
                }
            });
            timer.start();
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

        protected void done() {
            button1.setEnabled(true);
            boolean win = false;

            // Split the input betNumbers to get the array of numbers
            String[] numbersArray = betNumbers.split(",");

            for (String number : numbersArray) {
                try {
                    int chosenNumber = Integer.parseInt(number.trim());
                    if (chosenNumber < 0 || chosenNumber > 36&& !betType.equals("Color(Black or Red)")) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers (0-36).");
                        return;
                    }
                    // Determine payout based on the bet type
                    int payoutMultiplier = 0;

                    switch (betType) {
                        case "Straight Up (1 number)":
                            payoutMultiplier = 35;
                            if (rand == chosenNumber) {
                                win = true;
                                totalMoney += bet * payoutMultiplier;
                            }
                            break;
                        case "Split (2 numbers)":
                            payoutMultiplier = 17;
                            if (rand == chosenNumber || (numbersArray.length == 2 &&
                                    (rand == Integer.parseInt(numbersArray[0].trim()) ||
                                            rand == Integer.parseInt(numbersArray[1].trim())))) {
                                win = true;
                                totalMoney += bet * payoutMultiplier;
                            }
                            break;
                        case "Street (3 numbers)":
                            if (numbersArray.length == 3 &&
                                    (rand == Integer.parseInt(numbersArray[0].trim()) ||
                                            rand == Integer.parseInt(numbersArray[1].trim()) ||
                                            rand == Integer.parseInt(numbersArray[2].trim()))) {
                                win = true;
                                totalMoney += bet * 11;
                            }
                            break;
                        case "Corner (4 numbers)":
                            if (numbersArray.length == 4 &&
                                    (rand == Integer.parseInt(numbersArray[0].trim()) ||
                                            rand == Integer.parseInt(numbersArray[1].trim()) ||
                                            rand == Integer.parseInt(numbersArray[2].trim()) ||
                                            rand == Integer.parseInt(numbersArray[3].trim()))) {
                                win = true;
                                totalMoney += bet * 8;
                            }
                            break;
                        case "Color(Black or Red)":
                            if(comboBox2.getSelectedItem().equals("Black") && rand % 2 == 0) {
                                win = true;
                                totalMoney += bet * 2;
                            } else if(comboBox2.getSelectedItem().equals("Red") && rand % 2 != 0) {
                                win = true;
                                totalMoney += bet * 2;
                            }

                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
                    return;
                }
            }

            // Show result message
            if (win) {
                JOptionPane.showMessageDialog(null, "You win!");
            } else {
                JOptionPane.showMessageDialog(null, "You lose your bet of $" + bet);
            }
            updateMoneyDisplay(); // Update money display
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(kazino::new);

    }


    // JFormDesigner - Variables declaration - DO NOT MODI

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - ivaylo
        panel1 = new JPanel();
        textField1 = new JTextField();
        button1 = new JButton();
        moneyLabel = new JLabel();
        textField2 = new JTextField();
        comboBox1 = new JComboBox(bet1);
        label1 = new JLabel();
        label2 = new JLabel();
        comboBox2 = new JComboBox(bet3);
        panel2 = new JPanel();

        //======== panel1 ========
        {
            panel1.setBackground(Color.black);
            panel1.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(
            0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder
            .BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.
            red),panel1. getBorder()));panel1. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.
            beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName()))throw new RuntimeException();}});

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

            //---- textField2 ----
            textField2.setForeground(Color.white);
            textField2.setBackground(new Color(0x333333));
            textField2.setCaretColor(Color.white);

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

            //---- comboBox2 ----
            comboBox2.setBackground(new Color(0x333333));
            comboBox2.setForeground(Color.white);

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
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(panel2, GroupLayout.DEFAULT_SIZE, 1224, Short.MAX_VALUE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(comboBox2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                                    .addComponent(textField2, GroupLayout.Alignment.LEADING)
                                    .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(60, 60, 60)
                                .addComponent(comboBox1, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(label2, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button1, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(panel2, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textField2, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                    .addComponent(comboBox1, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label2, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                    .addComponent(button1, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
            );
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - ivaylo
    private JPanel panel1;
    private JTextField textField1;
    private JButton button1;
    private JLabel moneyLabel;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JLabel label1;
    private JLabel label2;
    private JComboBox comboBox2;
    private JPanel panel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
