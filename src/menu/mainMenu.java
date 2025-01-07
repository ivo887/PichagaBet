
package menu;

import Money.MoneyManager;
import aviator.Aviator;
import blackjack.blackjackGame;
import roulete.kazino;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainMenu extends JFrame {
    public mainMenu() {
        initComponents();
        JFrame frame = new JFrame("PichagaBet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.add(this.panel1);
        frame.setVisible(true);
        panel2.remove(button3);
        label1.setText("$ "+MoneyManager.getInstance().getTotalMoney());

        this.button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label1.setText("$ "+MoneyManager.getInstance().getTotalMoney());
                frame.remove(panel1);
                kazino kazinoPanel = new kazino();
                frame.add(kazinoPanel.panel1);
                kazinoPanel.panel1.add(button3);
                button3.setSize(frame.getWidth() / 18, frame.getHeight() / 18);
                frame.revalidate();
                frame.repaint();
            }
        });

        this.button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label1.setText("$ "+MoneyManager.getInstance().getTotalMoney());
                frame.remove(panel1);
                blackjackGame blackJackPanel = new blackjackGame();
                frame.add(blackJackPanel.mainPanel);
                blackJackPanel.mainPanel.add(button3, BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });

        this.button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label1.setText("$ "+MoneyManager.getInstance().getTotalMoney());
                panel2.remove(button3);
                frame.getContentPane().removeAll();
                frame.add(panel1);
                frame.revalidate();
                frame.repaint();
            }
        });

        this.button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label1.setText("$ "+MoneyManager.getInstance().getTotalMoney());
                frame.remove(panel1);
                Aviator aviatorPanel = new Aviator();
                frame.add(aviatorPanel);
                aviatorPanel.add(button3);
                frame.revalidate();
                frame.repaint();
            }
        });
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
        panel1 = new JPanel();
        panel2 = new JPanel();
        button3 = new JButton();
        panel4 = new JPanel();
        panel3 = new JPanel();
        button2 = new JButton();
        button1 = new JButton();
        button4 = new JButton();
        label1 = new JLabel();

        //======== panel1 ========
        {
            panel1.setBackground(Color.black);

            //======== panel2 ========
            {
                panel2.setBackground(Color.black);

                //---- button3 ----
                button3.setText("HOME");
                button3.setBackground(new Color(0x333333));
                button3.setForeground(Color.white);

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(button3)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(button3)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }

            //======== panel4 ========
            {
                panel4.setBackground(Color.black);

                //======== panel3 ========
                {
                    panel3.setBackground(Color.black);

                    //---- button2 ----
                    button2.setText("Blackjack");
                    button2.setBackground(new Color(0x333333));
                    button2.setForeground(Color.white);
                    button2.setPreferredSize(new Dimension(80, 34));

                    //---- button1 ----
                    button1.setText("Roulete");
                    button1.setForeground(Color.white);
                    button1.setBackground(new Color(0x333333));
                    button1.setPreferredSize(new Dimension(80, 34));

                    //---- button4 ----
                    button4.setText("Aviator");
                    button4.setForeground(Color.white);
                    button4.setBackground(new Color(0x333333));
                    button4.setPreferredSize(new Dimension(80, 34));

                    //---- label1 ----
                    label1.setText("text");
                    label1.setForeground(Color.white);
                    label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() & ~Font.BOLD, label1.getFont().getSize() + 14f));

                    GroupLayout panel3Layout = new GroupLayout(panel3);
                    panel3.setLayout(panel3Layout);
                    panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                            .addComponent(button2, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                            .addComponent(button4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                .addContainerGap(303, Short.MAX_VALUE)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                    panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(button2, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(button4, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(button1, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                .addGap(58, 58, 58))
                    );
                }

                GroupLayout panel4Layout = new GroupLayout(panel4);
                panel4.setLayout(panel4Layout);
                panel4Layout.setHorizontalGroup(
                    panel4Layout.createParallelGroup()
                        .addComponent(panel3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel4Layout.setVerticalGroup(
                    panel4Layout.createParallelGroup()
                        .addComponent(panel3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(mainMenu::new);

    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
    private JPanel panel1;
    private JPanel panel2;
    private JButton button3;
    private JPanel panel4;
    private JPanel panel3;
    private JButton button2;
    private JButton button1;
    private JButton button4;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}