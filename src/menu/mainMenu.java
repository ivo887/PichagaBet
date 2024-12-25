/*
 * Created by JFormDesigner on Fri Nov 08 10:39:17 EET 2024
 */

package menu;

import java.awt.*;

import aviator.Aviator;
import aviator.aviatorGame;
import org.w3c.dom.css.CSSStyleSheet;
import roulete.kazino;
import blackjack.blackjackGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
//import net.miginfocom.swing.*;

/**
 * @author martbul
 */
public class mainMenu extends JFrame {
    public int totalMoney = 100; // Starting money// Combo box for betting options

    //private MenuListener listener;
    public mainMenu() {
        initComponents();
        JFrame frame = new JFrame("PichagaBet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.add(this.panel1);
        frame.setVisible(true);
        panel2.remove(button3);


        this.button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel1);
                kazino kazinoPanel = new kazino(totalMoney);
                frame.add(kazinoPanel.panel1);
                kazinoPanel.panel1.add(button3);
                button3.setSize(frame.getWidth()/18, frame.getHeight()/18);
                frame.revalidate();
                frame.repaint();
            }
        });

        this.button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel1);
                blackjackGame blackJackPanel = new blackjackGame(totalMoney);
                frame.add(blackJackPanel.mainPanel);
                blackJackPanel.mainPanel.add(button3, BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            }
        });


        this.button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.remove(button3);
                frame.getContentPane().removeAll();
                frame.add(panel1);
                frame.revalidate();
                frame.repaint();
            }
        });

        // In your mainMenu.java class
        this.button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel1); // Clear the current panel
                Aviator aviatorPanel = new Aviator(totalMoney); // Create a new Aviator panel
                frame.add(aviatorPanel); // Add the Aviator panel
                aviatorPanel.add(button3); // Add the home button to the Aviator panel
                frame.revalidate(); // Revalidate the panel to apply changes
                frame.repaint(); // Repaint the panel to reflect changes
            }
        });

    }

    public int getTotalMoney() {
        return totalMoney;
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

                    GroupLayout panel3Layout = new GroupLayout(panel3);
                    panel3.setLayout(panel3Layout);
                    panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                            .addComponent(button2, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                            .addComponent(button4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                    panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(button2, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(button4, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(button1, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
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
                        .addGap(0, 0, Short.MAX_VALUE))
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
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}


