package blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class blackjackGame extends JPanel {
    private Deck deck;
    private Dealer dealer;
    private Player player;
    public int totalMoney;
    private int currentBet; // Field for current bet
    public JPanel mainPanel;

    public blackjackGame(int totalMoney) {
        this.totalMoney = totalMoney;
        this.currentBet = 0;

        deck = new Deck();
        dealer = new Dealer(deck);
        player = new Player(deck);
        initComponents();
    }


    private ImageIcon getCardImage(Card card) {
        String imagePath = "src/cards/" + card.getRank().name().toLowerCase() + "_of_" + card.getSuit().name().toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
        System.out.println(imagePath);
        return new ImageIcon(img);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel playerPanel = new JPanel(new FlowLayout());

        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        JLabel playerHandLabel = new JLabel("Player's Hand: ");
        playerPanel.add(playerHandLabel);


        JPanel dealerPanel = new JPanel(new FlowLayout());
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));
        JLabel dealerHandLabel = new JLabel("Dealer's Hand: ");

        dealerPanel.add(dealerHandLabel);

        JPanel buttonPanel = new JPanel();
        JButton dealToPlayerButton = new JButton("Deal to Player");
        JButton standButton = new JButton("Stand");
        buttonPanel.add(dealToPlayerButton);
        buttonPanel.add(standButton);

        JLabel totalMoneyLabel = new JLabel(totalMoney + " $");

        mainPanel.add(dealerPanel, BorderLayout.NORTH);
        mainPanel.add(playerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(totalMoneyLabel, BorderLayout.EAST);

        JLabel betLabel = new JLabel("Place your bet: ");
        JTextField betField = new JTextField(5);
        JButton placeBetButton = new JButton("Place Bet");
        JPanel bettingPanel = new JPanel();
        bettingPanel.add(betLabel);
        bettingPanel.add(betField);
        bettingPanel.add(placeBetButton);
        bettingPanel.add(totalMoneyLabel);


        setSize(500, 400);
        add(mainPanel);

            dealToPlayerButton.addActionListener(e -> {
            player.drawCard();
            updateHandPanel(playerPanel, player.getHand(), "Player");
            if (player.getHandValue() > 21) {
                JOptionPane.showMessageDialog(this, "Dealer wins.");
                resetGame();
            }
        });

        standButton.addActionListener(e -> {
            while (dealer.getHandValue() < 17) {
                dealer.drawCard();
            }
            updateHandPanel(dealerPanel, dealer.getHand(), "Dealer");
            determineWinner();
        });

//        standButton.addActionListener(e -> {
//            if (currentBet == 0) {
//                JOptionPane.showMessageDialog(this, "You must place a bet before starting the game!");
//                return;
//            }
//            while (dealer.getHandValue() < 17) {
//                dealer.drawCard();
//            }
//            updateHandPanel(dealerPanel, dealer.getHand(), "Dealer");
//            determineWinner(totalMoneyLabel);
//        });

        placeBetButton.addActionListener(e -> {
            try {
                int bet = Integer.parseInt(betField.getText());
                if (bet > totalMoney) {
                    JOptionPane.showMessageDialog(this, "You don't have enough money to place this bet!");
                } else if (bet <= 0) {
                    JOptionPane.showMessageDialog(this, "Bet must be greater than zero!");
                } else {
                    currentBet = bet;
                    JOptionPane.showMessageDialog(this, "Bet placed: $" + currentBet);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid bet amount!");
            }
        });





    }



    private void updateHandPanel(JPanel panel, List<Card> hand, String owner) {
        panel.removeAll();

        JLabel ownerLabel = new JLabel(owner + "'s Hand:");
        panel.add(ownerLabel);

        for (Card card : hand) {
            JLabel cardLabel = new JLabel(getCardImage(card));
            panel.add(cardLabel);
        }

        int totalValue = owner.equals("Dealer") ? dealer.getHandValue() : player.getHandValue();
        JLabel totalLabel = new JLabel("Total: " + totalValue);
        panel.add(totalLabel);

        panel.revalidate();
        panel.repaint();
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

        updateHandPanel((JPanel) mainPanel.getComponent(0), dealer.getHand(), "Dealer");
        updateHandPanel((JPanel) mainPanel.getComponent(1), player.getHand(), "Player");
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

        public Suit getSuit() {
            return suit;
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
