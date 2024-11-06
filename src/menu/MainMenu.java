package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import roulete.kazino; // Import your roulete.kazino package here

public class MainMenu extends JFrame {
    public MainMenu() {
        // Set up the main menu window
        setTitle("Game Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a button to open roulete.kazino game
        JButton openKazinoButton = new JButton("Open Kazino Game");
        openKazinoButton.setPreferredSize(new Dimension(200, 50));

        // Add action listener to the button to open the roulete.kazino game
        openKazinoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Launch the roulete.kazino game window
                new kazino(); // Create an instance of roulete.kazino to open the game
            }
        });

        // Set up the layout and add button to frame
        setLayout(new FlowLayout());
        add(openKazinoButton);
    }

    public static void main(String[] args) {
        // Start the main menu GUI
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}
