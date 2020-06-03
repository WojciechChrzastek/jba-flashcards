package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    runApplication();
  }

  static void runApplication() {
    Map<String, String> cards = createCardMap();
    Map<String, Integer> hardestCards = createHardestCardMap();
    List<String> logs = createLogList();

    while (true) {
      String action = askForAction(logs);
      executeAction(action, cards, logs, hardestCards);
    }
  }

  private static Map<String, Integer> createHardestCardMap() {

    return new HashMap<>();
  }

  static String askForAction(List<String> logs) {
    String output;
    List<String> actions = Arrays.asList("add", "remove", "import", "export", "ask", "l", "log", "hardest card", "exit");
    boolean isAction;
    String action;
    Scanner scanner = new Scanner(System.in);
    output = "Input the action (add, remove, import, export, ask, l, log, hardest card, exit):";
    System.out.println(output);
    logs.add(output);
    do {
      String input;
      action = scanner.nextLine();
      input = action;
      logs.add(input);
      isAction = actions.contains(action);
      if (!isAction) {
        output = "Please input a valid action (add, remove, import, export, ask, l, log, hardest card, exit):";
        System.out.println(output);
        logs.add(output);
      }
    }
    while (!isAction);
    return action;
  }

  static void executeAction(String action, Map<String, String> cards, List<String> logs, Map<String, Integer> hardestCards) {
    String output;
    switch (action) {
      case "add":
        createCard(cards, logs);
        break;
      case "remove":
        removeCard(cards, logs);
        break;
      case "import":
        importCards(cards, logs);
        break;
      case "export":
        exportCards(cards, logs);
        break;
      case "ask":
        if (cards.size() > 0) {
          askForDefinition(determineAskCount(cards, logs), cards, logs);
        } else {
          output = "The cards set is empty!\n";
          System.out.println(output);
          logs.add(output);
        }
        break;
      case "l":
        System.out.println(cards);
        break;
      case "log":
        saveLog(logs);
        break;
      case "hardest card":
        if (hardestCards.size() > 0) {
          showHardestCard(logs);
        } else {
          output = "There are no cards with errors.\n";
          System.out.println(output);
          logs.add(output);
        }
        break;
      case "exit":
        output = "Bye bye!";
        System.out.println(output);
        logs.add(output);
        System.exit(0);
        break;
    }
  }

  private static void showHardestCard(List<String> logs) {

  }

  static List<String> createLogList() {

//    List<String> logs = new ArrayList<>();
//    logs.add("Justin");
//    logs.add("Helen");
//    logs.add("Joshua");
//
//    return logs;
    return new ArrayList<>();
  }


  static void saveLog(List<String> logs) {
    String output;
    Scanner scanner = new Scanner(System.in);
    output = "File name:";
    System.out.println("File name:");
    logs.add(output);
    String input = scanner.next();
    logs.add(input);
    File file = new File(input);

    try (FileWriter writer = new FileWriter(file)) {

      for (String log : logs) {
        writer.write(log + "\n");
      }

      output = "The log has been saved.";
      System.out.println(output);
      logs.add(output);
    } catch (IOException e) {
      System.out.printf("An exception occurs %s", e.getMessage());
    }
  }

  static void exportCards(Map<String, String> cards, List<String> logs) {
    String output;
    String input;
    Scanner scanner = new Scanner(System.in);
    output = "File name:";
    System.out.println(output);
    logs.add(output);
    input = scanner.next();
    logs.add(input);
    File file = new File(input);

//    for (var entry : map.entrySet()) {
//      if (entry.getValue().equals(value)) {
//        return entry.getKey();
//      }
//    }

    try (FileWriter writer = new FileWriter(file)) {
      int counter = 0;
      for (var entry : cards.entrySet()) {
        writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
        counter++;
      }
      output = counter + " cards have been saved.";
      System.out.println(output);
      logs.add(output);
    } catch (IOException e) {
      System.out.printf("An exception occurs %s", e.getMessage());
    }
  }

  static void importCards(Map<String, String> cards, List<String> logs) {
    String output;
    String input;
    Scanner scannerInput = new Scanner(System.in);
    output = "File name:";
    System.out.println(output);
    logs.add(output);
    String path = scannerInput.next();
    input = path;
    logs.add(input);
    File file = new File(path);
    int counter = 0;

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String entrySetString = scanner.nextLine();
        String[] entrySet = entrySetString.split("=");
        cards.put(entrySet[0], entrySet[1]);
        counter++;
      }
      output = counter + " cards have been loaded.";
      System.out.println(output);
      logs.add(output);
    } catch (FileNotFoundException e) {
      output = "File not found.\n";
      System.out.println(output);
      logs.add(output);
    }

  }

  static Map<String, String> createCardMap() {

//    Map<String, String> cardMap = new HashMap<>();
//    cardMap.put("1", "a");
//    cardMap.put("2", "b");
//    cardMap.put("3", "c");
//
//    return cardMap;
    return new HashMap<>();
  }

  static void removeCard(Map<String, String> cards, List<String> logs) {
    String output;
    String input;
    Scanner scanner = new Scanner(System.in);
    String term;
    output = "The card:";
    System.out.println(output);
    logs.add(output);
    term = scanner.nextLine();
    input = term;
    logs.add(input);
    if (cards.containsKey(term)) {
      cards.remove(term);
      output = "The card has been removed.\n";
      System.out.println(output);
      logs.add(output);
    } else {
      System.out.printf("Can't remove \"%s\": there is no such card.\n\n", term);
    }
  }

  static void createCard(Map<String, String> cards, List<String> logs) {
    String output;
    String input;
    Scanner scanner = new Scanner(System.in);
    String term;
    String definition;

    output = "The card:";
    System.out.println(output);
    logs.add(output);
    term = scanner.nextLine();
    input = term;
    logs.add(input);
    if (cards.containsKey(term)) {
      System.out.printf("The card \"%s\" already exists.\n\n", term);
      return;
    }

    output = "The definition of the card:";
    System.out.println(output);
    logs.add(output);
    definition = scanner.nextLine();
    input = definition;
    logs.add(input);
    if (cards.containsValue(definition)) {
      System.out.printf("The definition \"%s\" already exists.\n\n", definition);
      return;
    }

    cards.putIfAbsent(term, definition);
    System.out.printf("The pair (\"%s\":\"%s\") has been added.\n\n", term, definition);
  }

  static int determineAskCount(Map<String, String> cards, List<String> logs) {
    String output;
    String input;
    Scanner scanner = new Scanner(System.in);
    int askCount;
    output = "How many times to ask?";
    System.out.println(output);
    logs.add(output);
//    while (scanner.hasNext()) {
//      if (scanner.hasNextInt()) {
    askCount = scanner.nextInt();
    input = String.valueOf(askCount);
    logs.add(input);
//        if (askCount > 0 && askCount <= cards.size()) {
    return askCount;
//        }
//      } else {
//        scanner.next();
//      }
//      System.out.printf("Please input the valid number from 1 to %s:\n", cards.size());
//    }
//    return -1;
  }

  static void askForDefinition(int askCount, Map<String, String> cards, List<String> logs) {
    String output;
    String input;

    for (int i = 0; i < askCount; i++) {

//      for (Map.Entry<String, String> entry : cards.entrySet()) {

      Map.Entry<String, String> entry = cards.entrySet().iterator().next();

      output = "Print the definition of \"" + entry.getKey() + "\":";
      System.out.println(output);
      logs.add(output);
      String answer = new Scanner(System.in).nextLine();
      input = answer;
      logs.add(input);

      if (answer.equals(entry.getValue())) {
        output = "Correct answer.";
        System.out.println(output);
        logs.add(output);
      } else {
        if (cards.containsValue(answer)) {
          output = "Wrong answer. The correct one is \"" + entry.getValue() + "\"," +
                  " you've just written the definition of \"" + getKey(cards, answer) + "\".";
          System.out.println(output);
          logs.add(output);
        } else {
          output = "Wrong answer. The correct one is \"" + entry.getValue() + "\".";
          System.out.println(output);
          logs.add(output);
        }
      }

//      }
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


//  static void playGame() {
//    int numberOfCards = chooseNumberOfCards();
//    HashMap<String, String> cards = createCardMap(numberOfCards);
//    askForDefinition(cards);
//  }
//
//  static int chooseNumberOfCards() {
//    Scanner scanner = new Scanner(System.in);
//    System.out.println("Input the number of cards:");
//    while (scanner.hasNext()) {
//      if (scanner.hasNextInt()) {
//        int input = scanner.nextInt();
//        if (input > 0 && input < 10) {
//          return input;
//        }
//      } else {
//        scanner.next();
//      }
//      System.out.println("Please input the number of cards from 1 to 10:");
//    }
//    return -1;
//  }
//
//  static class Card {
//    String term;
//    String definition;
//
//    public Card(String term, String definition) {
//      this.term = term;
//      this.definition = definition;
//    }
//
//    String getTerm() {
//      return term;
//    }
//
//    String getDefinition() {
//      return definition;
//    }
//
//    void setTerm(String term) {
//      this.term = term;
//    }
//
//    void setDefinition(String definition) {
//      this.definition = definition;
//    }
//  }


//  static HashMap<String, String> createCardMap(int numberOfCards) {
//    int cardCount = 1;
//    HashMap<String, String> cards = new HashMap<>();
//
//    for (int i = 0; i < numberOfCards; i++) {
//      Scanner scanner = new Scanner(System.in);
//      String term = "";
//      String definition = "";
//
//      System.out.println("The card #" + cardCount + ":");
//      while (scanner.hasNext()) {
//        term = scanner.nextLine();
//        if (!cards.containsKey(term)) {
//          break;
//        } else {
//          System.out.println("The card \"" + term + "\" already exists. Try again:");
//        }
//      }
//
//      System.out.println("The definition of the card #" + cardCount + ":");
//      while (scanner.hasNext()) {
//        definition = scanner.nextLine();
//        if (!cards.containsValue(definition)) {
//          break;
//        } else {
//          System.out.println("The definition \"" + definition + "\" already exists. Try again:");
//        }
//      }
//
//      cards.putIfAbsent(term, definition);
//      cardCount++;
//    }
//    return cards;
//  }

