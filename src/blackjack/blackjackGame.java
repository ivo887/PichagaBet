package blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Main Blackjack Game class with GUI and Dealer integration
public class blackjackGame extends JFrame {
    private Dealer dealer;
    private int cards = 52;

    public blackjackGame() {
        dealer = new Dealer(); // Initialize the Dealer
        initComponents();
    }

    private void initComponents() {
        panel1 = new JPanel();
        dealButton = new JButton("Deal Card to Dealer");
        dealerHandLabel = new JLabel("Dealer's Hand: ");

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());
            panel1.add(dealButton, BorderLayout.SOUTH);
            panel1.add(dealerHandLabel, BorderLayout.CENTER);
        }

        // Set up button action
        dealButton.addActionListener(e -> dealToDealer());

        // Configure the main frame
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        add(panel1);
    }

    // Method to handle dealing a card to the dealer
    private void dealToDealer() {
        dealer.drawCard();
        updateDealerHand();
        if (dealer.getHandValue() >= 21) {
            JOptionPane.showMessageDialog(this, "Dealer stands with hand value: " + dealer.getHandValue());
        }
    }

    // Method to update the dealer's hand display
    private void updateDealerHand() {
        StringBuilder handText = new StringBuilder("Dealer's Hand: ");
        for (Card card : dealer.getDealerHand()) {
            handText.append(card).append(" ");
        }
        handText.append("(Total: ").append(dealer.getHandValue()).append(")");
        dealerHandLabel.setText(handText.toString());
    }

    // Main method to run the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new blackjackGame().setVisible(true));
    }

    // JFormDesigner - Variables declaration
    private JPanel panel1;
    private JButton dealButton;
    private JLabel dealerHandLabel;

    // Inner Dealer class
    class Dealer {
        private List<Card> deck;
        private List<Card> dealerHand;

        public Dealer() {
            this.deck = new ArrayList<>();
            this.dealerHand = new ArrayList<>();
            initializeDeck();
            shuffleDeck();
        }

        // Initialize a standard 52-card deck
        private void initializeDeck() {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    deck.add(new Card(rank, suit));
                }
            }
        }

        // Shuffle the deck
        public void shuffleDeck() {
            Collections.shuffle(deck, new Random());
        }

        // Deal a card from the deck
        public Card dealCard() {
            if (deck.isEmpty()) {
                initializeDeck();
                shuffleDeck();
            }
            return deck.remove(deck.size() - 1);
        }

        // Dealer adds a card to their hand
        public void drawCard() {
            dealerHand.add(dealCard());
        }

        // Get the total value of the dealer's hand
        public int getHandValue() {
            int value = 0;
            int aces = 0;

            for (Card card : dealerHand) {
                value += card.getValue();
                if (card.getRank() == Rank.ACE) {
                    aces++;
                }
            }

            // Adjust for Aces (1 or 11)
            while (value > 21 && aces > 0) {
                value -= 10;
                aces--;
            }

            return value;
        }

        // Accessor for the dealer's hand
        public List<Card> getDealerHand() {
            return dealerHand;
        }
    }

    // Inner Card class
    class Card {
        private final Rank rank;
        private final Suit suit;

        public Card(Rank rank, Suit suit) {
            this.rank = rank;
            this.suit = suit;
        }

        public int getValue() {
            return rank.getValue();
        }

        public Rank getRank() {
            return rank;
        }

        @Override
        public String toString() {
            return rank + " of " + suit;
        }
    }

    // Enum for card suits
    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES;
    }

    // Enum for card ranks
    enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(11);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
