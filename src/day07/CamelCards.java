package day07;

import utils.Advent;

import java.util.ArrayList;
import java.util.Arrays;

public class CamelCards {
    static class Hand implements Comparable<Hand> {
        int[] cards = new int[5];
        int bid;
        /*
        * (0) Five of a kind - AAAAA
        *
        * (1) Four of a kind - AA8AA
        *
        * (2) Full house - 23332
        * (3) Three of a kind - TTT98
        *
        * (4) Two pair - 23432
        * (5) One pair - A23A4
        *
        * (6) High card, where all cards' labels are distinct: 23456
        */
        int type;

        public Hand(String hand) {
            String[] cardsAndBid = hand.split(" ");
            char[] cardsCharArr = cardsAndBid[0].toCharArray();
            this.bid = Integer.parseInt(cardsAndBid[1]);

            buildCards(cardsCharArr);

            // generate frequency distribution and set type
            int[] histogram = populateHistogram();
            Arrays.sort(histogram); // sorts from smallest to largest
            if (histogram[12] == 5) this.type = 0; // five of a kind
            else if (histogram[12] == 4) this.type = 1; // four of a kind
            else if (histogram[12] == 3) {
                if (histogram[11] == 2) {
                    this.type = 2; // full house
                } else {
                    this.type = 3; // three of a kind
                }
            } else if (histogram[12] == 2) {
                if (histogram[11] == 2) {
                    this.type = 4; // two pairs
                } else {
                    this.type = 5; // one pair
                }
            } else {
                this.type = 6; // high card
            }
        }

        void buildCards(char[] cardsCharArr) {
            for (int i = 0; i < cardsCharArr.length; i++) {
                char card = cardsCharArr[i];
                switch (card) {
                    case 'T' -> this.cards[i] = 10;
                    case 'J' -> this.cards[i] = 11;
                    case 'Q' -> this.cards[i] = 12;
                    case 'K' -> this.cards[i] = 13;
                    case 'A' -> this.cards[i] = 14;
                    default -> this.cards[i] = card - '0';
                }
            }
        }

        int[] populateHistogram() {
            int[] histogram = new int[13];
            for (int card : this.cards) {
                histogram[card - 2]++;
            }
            return histogram;
        }

        @Override
        public int compareTo(Hand o) {
            if (this.type != o.type) {
                return o.type - this.type;
            }

            for (int i = 0; i < 5; i++) {
                if (this.cards[i] != o.cards[i]) {
                    return this.cards[i] - o.cards[i];
                }
            }

            return 0;
        }
    }

    static class JokerHand extends Hand {
        public JokerHand(String hand) {
            super(hand);
        }

        @Override
        void buildCards(char[] cardsCharArr) {
            for (int i = 0; i < cardsCharArr.length; i++) {
                char card = cardsCharArr[i];
                switch (card) {
                    case 'J' -> this.cards[i] = 2;
                    case 'T' -> this.cards[i] = 11;
                    case 'Q' -> this.cards[i] = 12;
                    case 'K' -> this.cards[i] = 13;
                    case 'A' -> this.cards[i] = 14;
                    default -> this.cards[i] = card - '0' + 1;
                }
            }
        }

        @Override
        int[] populateHistogram() {
            int[] histogram = super.populateHistogram();
            int numOfJokers = histogram[0];
            histogram[0] = 0;
            Arrays.sort(histogram);
            histogram[12] += numOfJokers;
            return histogram;
        }
    }

    static int calculateTotalWinnings(Hand[] hands) {
        Arrays.sort(hands);

        int totalWinnings = 0;

        for (int i = 0; i < hands.length; i++) {
            totalWinnings += hands[i].bid * (i + 1);
        }

        return totalWinnings;
    }

    public static void main(String[] args) {
        ArrayList<String> input = Advent.readInput();

        Hand[] hands = new Hand[input.size()];
        JokerHand[] jokerHands = new JokerHand[input.size()];
        for (int i = 0; i < hands.length; i++) {
            hands[i] = new Hand(input.get(i));
            jokerHands[i] = new JokerHand(input.get(i));
        }

        System.out.println(calculateTotalWinnings(hands));
        System.out.println(calculateTotalWinnings(jokerHands));
    }
}
