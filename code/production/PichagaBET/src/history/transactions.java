package history;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class transactions extends JPanel {
    public transactions() {
        initComponents();
        loadHistory();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
        scrollPane1 = new JScrollPane();
        textPane1 = new JTextPane();

        //======== this ========
        setForeground(new Color(0x333333));
        setBackground(new Color(0x333333));

        //======== scrollPane1 ========
        {
            scrollPane1.setBackground(new Color(0x333333));
            scrollPane1.setBorder(null);
            scrollPane1.setForeground(new Color(0x333333));

            //---- textPane1 ----
            textPane1.setForeground(Color.white);
            textPane1.setBackground(new Color(0x333333));
            textPane1.setCaretColor(Color.white);
            textPane1.setEditable(false);
            textPane1.setFont(textPane1.getFont().deriveFont(textPane1.getFont().getSize() + 7f));
            textPane1.setAlignmentX(0.0F);
            textPane1.setAlignmentY(0.0F);
            scrollPane1.setViewportView(textPane1);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(scrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private void loadHistory() {
        try {
            File myObj = new File("src/history/history.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                textPane1.setText(textPane1.getText() + data + "\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Ivaylo Yordanov (ivayloay)
    private JScrollPane scrollPane1;
    private JTextPane textPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
