package blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class blackjackGame extends JFrame {
    private Deck deck;
    private Dealer dealer;
    private Player player;

    public blackjackGame() {
        deck = new Deck();
        dealer = new Dealer(deck);
        player = new Player(deck);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        JButton dealToPlayerButton = new JButton("Deal to Player");
     //   JButton dealToDealerButton = new JButton("Deal to Dealer");
        JButton standButton = new JButton("Stand");
        JLabel dealerHandLabel = new JLabel("Dealer's Hand: ");
        JLabel playerHandLabel = new JLabel("Player's Hand: ");

        // Panel layout
        panel.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(dealToPlayerButton);
    //    buttonPanel.add(dealToDealerButton);
        buttonPanel.add(standButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(dealerHandLabel, BorderLayout.NORTH);
        panel.add(playerHandLabel, BorderLayout.CENTER);

        // Button actions
        dealToPlayerButton.addActionListener(e -> {
            player.drawCard();
            updateHandLabel(playerHandLabel, player.getHand(), "Player");
            if (player.getHandValue() > 21) {
                JOptionPane.showMessageDialog(this, "Dealer wins.");
                resetGame();
            }
        });

//        dealToDealerButton.addActionListener(e -> {
//            dealer.drawCard();
//            updateHandLabel(dealerHandLabel, dealer.getHand(), "Dealer");
//            if (dealer.getHandValue() >= 17) {
//                JOptionPane.showMessageDialog(this, "Dealer stands.");
//            }
//        });

        standButton.addActionListener(e -> {
            while (dealer.getHandValue() < 17) {
                dealer.drawCard();
            }
            updateHandLabel(dealerHandLabel, dealer.getHand(), "Dealer");
            determineWinner();
        });

        // Frame configuration
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        add(panel);
    }

    private void updateHandLabel(JLabel label, List<Card> hand, String owner) {
        StringBuilder text = new StringBuilder(owner + "'s Hand: ");
        for (Card card : hand) {
            text.append(card).append(" ");
        }
        int totalValue = owner.equals("Dealer") ? dealer.getHandValue() : player.getHandValue();
        text.append("(Total: ").append(totalValue).append(")");
        label.setText(text.toString());
    }

    private void determineWinner() {
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();

        if (dealerValue > 21 || (playerValue <= 21 && playerValue > dealerValue)) {
            JOptionPane.showMessageDialog(this, "Player wins!");
        } else if (playerValue > 21 || dealerValue > playerValue) {
            JOptionPane.showMessageDialog(this, "Dealer wins!");
        } else {
            JOptionPane.showMessageDialog(this, "It's a tie!");
        }
        resetGame();
    }

    private void resetGame() {
        deck.reset();
        dealer.resetHand();
        player.resetHand();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new blackjackGame().setVisible(true));
    }

    // Shared Deck class
    static class Deck {
        private List<Card> cards;

        public Deck() {
            cards = new ArrayList<>();
            initialize();
            shuffle();
        }

        private void initialize() {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    cards.add(new Card(rank, suit));
                }
            }
        }

        public void shuffle() {
            Collections.shuffle(cards, new Random());
        }

        public Card draw() {
            if (cards.isEmpty()) {
                initialize();
                shuffle();
            }
            return cards.remove(cards.size() - 1);
        }

        public void reset() {
            cards.clear();
            initialize();
            shuffle();
        }
    }

    // Dealer class
    static class Dealer {
        private List<Card> hand;
        private Deck deck;

        public Dealer(Deck deck) {
            this.deck = deck;
            this.hand = new ArrayList<>();
        }

        public void drawCard() {
            hand.add(deck.draw());
        }

        public List<Card> getHand() {
            return hand;
        }

        public int getHandValue() {
            return calculateHandValue();
        }

        private int calculateHandValue() {
            int value = 0;
            int aces = 0;

            for (Card card : hand) {
                value += card.getValue();
                if (card.getRank() == Rank.ACE) {
                    aces++;
                }
            }

            while (value > 21 && aces > 0) {
                value -= 10;
                aces--;
            }
            return value;
        }

        public void resetHand() {
            hand.clear();
        }
    }

    // Player class
    static class Player extends Dealer {
        public Player(Deck deck) {
            super(deck);
        }
    }

    // Card class
    static class Card {
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

