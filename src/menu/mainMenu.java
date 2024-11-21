/*
 * Created by JFormDesigner on Fri Nov 08 10:39:17 EET 2024
 */

package menu;

import java.awt.*;
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
        JFrame frame = new JFrame("Pichaga");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(this.panel1);
        frame.setVisible(true);


        this.button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);

                new kazino(totalMoney);
            }
        });

        this.button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);


                blackjackGame game = new blackjackGame(totalMoney);
                game.setVisible(true);
            }
        });
    }
//    public void setMenuListener(MenuListener listener) {
//        this.listener = listener;
//    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
        panel1 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();

        //======== panel1 ========
        {
            panel1.setBackground(Color.black);

            //---- button1 ----
            button1.setText("Roulete");
            button1.setForeground(Color.white);
            button1.setBackground(new Color(0x333333));

            //---- button2 ----
            button2.setText("Blackjack");
            button2.setBackground(new Color(0x333333));
            button2.setForeground(Color.white);

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(button1, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(button2, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                        .addGap(50, 50, 50))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(button1, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(button2, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                        .addGap(161, 161, 161))
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
    private JButton button1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}


