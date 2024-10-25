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

    public kazino() {
        initComponents();
        JFrame frame = new JFrame("PichagaBET");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(this.panel1);
        frame.setVisible(true);

        // Set initial money display
        updateMoneyDisplay();

        this.button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int bet = Integer.parseInt(textField1.getText());
                    String betNumbers = textField2.getText().trim(); // Get bet numbers
                    if (bet > totalMoney || bet <= 0) {
                        JOptionPane.showMessageDialog(frame, "Invalid bet amount!");
                        return;
                    }
                    totalMoney -= bet; // Deduct bet from total money
                    button1.setEnabled(false);
                    progressBar1.setMaximum(36);
                    Random rand = new Random();
                    int rand_int1 = rand.nextInt(37);
                    new ProgressTask(rand_int1, bet, (String) comboBox1.getSelectedItem(), betNumbers).execute(); // Pass bet type and numbers
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
            }
        });
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
                progressBar1.setValue(value);
                button1.setText(Integer.toString(value));
                progressBar1.setForeground(value % 2 == 0 ? Color.black : Color.red);
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
                    if (chosenNumber < 0 || chosenNumber > 36) {
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
                        case "6-Line (6 numbers)":
                            if (numbersArray.length <= 6 &&
                                    (rand <= 6)) { // For simplicity, assume 1-6 are valid
                                win = true;
                                totalMoney += bet * 5;
                            }
                            break;
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
        progressBar1 = new JProgressBar();
        textField1 = new JTextField();
        button1 = new JButton();
        moneyLabel = new JLabel();
        comboBox1 = new JComboBox();
        textField2 = new JTextField();

        //======== panel1 ========
        {
            panel1.setBackground(Color.black);
            panel1.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax
            . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e" , javax. swing
            .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .
            Font ( "Dialo\u0067", java .awt . Font. BOLD ,12 ) ,java . awt. Color .red
            ) ,panel1. getBorder () ) ); panel1. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override
            public void propertyChange (java . beans. PropertyChangeEvent e) { if( "borde\u0072" .equals ( e. getPropertyName (
            ) ) )throw new RuntimeException( ) ;} } );

            //---- progressBar1 ----
            progressBar1.setBackground(new Color(0x333333));

            //---- textField1 ----
            textField1.setBackground(new Color(0x333333));

            //---- button1 ----
            button1.setText("text");
            button1.setBackground(new Color(0x333333));

            //---- moneyLabel ----
            moneyLabel.setBackground(Color.white);
            moneyLabel.setForeground(Color.white);

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap(26, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                    .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button1)
                                    .addGap(148, 148, 148))
                                .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                    .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 388, GroupLayout.PREFERRED_SIZE)
                                    .addGap(65, 65, 65))
                                .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                    .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27)))))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(moneyLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(button1)
                            .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
            );
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - ivaylo
    private JPanel panel1;
    private JProgressBar progressBar1;
    private JTextField textField1;
    private JButton button1;
    private JLabel moneyLabel;
    private JComboBox comboBox1;
    private JTextField textField2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
