/*
 * Created by JFormDesigner on Wed Dec 25 20:22:35 EET 2024
 */

package aviator;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author ivayl
 */
public class Aviator extends JPanel {
    public Aviator(int totalMoney) {
        initComponents();
        this.setVisible(true);
        Money.setText("Money: $" + totalMoney);

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
        Amount = new JTextField();
        Animation = new JPanel();
        Bet = new JButton();
        Out = new JButton();
        Money = new JLabel();

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
        Bet.setText("text");
        Bet.setBackground(new Color(0x333333));
        Bet.setForeground(Color.white);

        //---- Out ----
        Out.setText("text");
        Out.setBackground(new Color(0x333333));
        Out.setForeground(Color.white);

        //---- Money ----
        Money.setText("text");
        Money.setBackground(Color.black);
        Money.setForeground(Color.white);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(588, Short.MAX_VALUE)
                    .addComponent(Money, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                    .addGap(35, 35, 35))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
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
                    .addGap(28, 28, 28)
                    .addComponent(Money, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(Animation, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(Out, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addComponent(Bet, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addComponent(Amount, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
