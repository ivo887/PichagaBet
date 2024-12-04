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
    public int totalMoney;

    public blackjackGame(int totalMoney) {
        this.totalMoney = totalMoney;

        deck = new Deck();
        dealer = new Dealer(deck);
        player = new Player(deck);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Player Panel
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        JLabel playerHandLabel = new JLabel("Player's Hand: ");
        playerPanel.add(playerHandLabel);

        // Dealer Panel
        JPanel dealerPanel = new JPanel();
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));
        JLabel dealerHandLabel = new JLabel("Dealer's Hand: ");

        dealerPanel.add(dealerHandLabel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton dealToPlayerButton = new JButton("Deal to Player");
        JButton standButton = new JButton("Stand");
        buttonPanel.add(dealToPlayerButton);
        buttonPanel.add(standButton);

        // Total Money Label
        JLabel totalMoneyLabel = new JLabel(totalMoney + " $");

        // Add components to the main panel
        mainPanel.add(dealerPanel, BorderLayout.NORTH);
        mainPanel.add(playerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(totalMoneyLabel, BorderLayout.EAST);

        // Set up frame
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        add(mainPanel);

        // Button actions
        dealToPlayerButton.addActionListener(e -> {
            player.drawCard();
            updateHandLabel(playerHandLabel, player.getHand(), "Player");
            if (player.getHandValue() > 21) {
                JOptionPane.showMessageDialog(this, "Dealer wins.");
                resetGame();
            }
        });

        standButton.addActionListener(e -> {
            while (dealer.getHandValue() < 17) {
                dealer.drawCard();
            }
            updateHandLabel(dealerHandLabel, dealer.getHand(), "Dealer");
            determineWinner();
        });
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


    static class Player extends Dealer {
        public Player(Deck deck) {
            super(deck);
        }
    }


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


    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES;
    }

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
