package flashcards;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int numberOfCards = chooseNumberOfCards();
        Card[] cards = createCardArray(numberOfCards);
        playGame(cards);
    }

    static int chooseNumberOfCards() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the number of cards:");
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input > 0 && input < 10) {
                    return input;
                }
            } else {
                scanner.next();
            }
            System.out.println("Please input the number of cards from 1 to 10:");
        }
        return -1;
    }

    static Card[] createCardArray(int numberOfCards) {
        int cardCount = 1;
        Card[] cards = new Card[numberOfCards];
        for (int i = 0; i < numberOfCards; i++) {
            cards[i] = createCard(cardCount);
            cardCount++;
        }
        return cards;
    }

    static Card createCard(int cardCount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The card #" + cardCount + ":");
        String term = scanner.nextLine();
        System.out.println("The definition of the card #" + cardCount + ":");
        String definition = scanner.nextLine();
        return new Card(term, definition);
    }

    static class Card {
        String term;
        String definition;

        Card(String term, String definition) {
            this.term = term;
            this.definition = definition;
        }

        public String getTerm() {
            return term;
        }

        public String getDefinition() {
            return definition;
        }
        public String toString() {
            return term + ":" + definition;
        }
    }

    static void playGame(Card[] cards) {
        for (Card card : cards) {
            String answer = askForDefinition(card);
            printResult(card.getDefinition(), answer);
        }
    }

    static String askForDefinition(Card card) {
            System.out.println("Print the definition of");
            System.out.println("\"" + card.getTerm() + "\":");
            String answer = new Scanner(System.in).nextLine();;
        return answer;
    }

    static void printResult(String definition, String answer) {
        if (answer.equals(definition)) {
            System.out.printf("Correct answer.");
        } else {
            System.out.printf("Wrong answer. The correct one is \"" + definition + "\".");
        }
    }
}
