package flashcards;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    playGame();
  }

  static void playGame() {
    int numberOfCards = chooseNumberOfCards();
    LinkedHashMap<String, String> cards = createCardMap(numberOfCards);
    askForDefinition(cards);
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

  static LinkedHashMap<String, String> createCardMap(int numberOfCards) {
    int cardCount = 1;
    LinkedHashMap<String, String> cards = new LinkedHashMap<>();

    for (int i = 0; i < numberOfCards; i++) {
      Scanner scanner = new Scanner(System.in);
      String term = "";
      String definition = "";

      System.out.println("The card #" + cardCount + ":");
      while (scanner.hasNext()) {
        term = scanner.nextLine();
        if (!cards.containsKey(term)) {
          break;
        } else {
          System.out.println("The card \"" + term + "\" already exists. Try again:");
        }
      }

      System.out.println("The definition of the card #" + cardCount + ":");
      while (scanner.hasNext()) {
        definition = scanner.nextLine();
        if (!cards.containsValue(definition)) {
          break;
        } else {
          System.out.println("The definition \"" + definition + "\" already exists. Try again:");
        }
      }

      cards.putIfAbsent(term, definition);
      cardCount++;
    }
    return cards;
  }

  static void askForDefinition(LinkedHashMap<String, String> cards) {
    for (var entry : cards.entrySet()) {
      System.out.println("Print the definition of \"" + entry.getKey() + "\":");
      String answer = new Scanner(System.in).nextLine();
      String key = entry.getKey();
      if (answer.equals(entry.getValue())) {
        System.out.println("Correct answer.");
      } else {
        if (cards.containsValue(answer)) {
          System.out.println("Wrong answer. The correct one is \"" + entry.getValue() + "\"," +
                  " you've just written the definition of \"" + getKey(cards, answer) + "\".");
        } else {
          System.out.println("Wrong answer. The correct one is \"" + entry.getValue() + "\".");
        }
      }
    }
  }

  public static String getKey(Map<String, String> map, String value) {
    for (var entry : map.entrySet()) {
      if (entry.getValue().equals(value)) {
        return entry.getKey();
      }
    }
    return null;
  }
}
