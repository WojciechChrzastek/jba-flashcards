package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    runApplication();
  }

  static void runApplication() {
    Map<Card, Integer> cards = new HashMap<>();
    List<String> hardestTerms = new ArrayList<>();
    List<String> logs = createLogList();
    String action = "";

    while (!action.equals("exit")) {
      action = askForAction(logs);
      executeAction(action, cards, logs, hardestTerms);
    }
  }

  static String askForAction(List<String> logs) {
    String output;
    List<String> actions = Arrays.asList("add", "remove", "import", "export", "ask", "exit", "log", "hardest card", "reset stats");
    boolean isAction;
    String action;
    Scanner scanner = new Scanner(System.in);
    output = "\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
    System.out.println(output);
    logs.add(output);
    do {
      String input;
      action = scanner.nextLine();
      input = action;
      logs.add(input);
      isAction = actions.contains(action);
      if (!isAction) {
        output = "\nPlease input a valid action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
        System.out.println(output);
        logs.add(output);
      }
    }
    while (!isAction);
    return action;
  }

  static void executeAction(String action, Map<Card, Integer> cards, List<String> logs, List<String> hardestTerms) {
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
          askForDefinition(determineAskCount(logs), cards, logs);
        } else {
          output = "The cards set is empty!\n";
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
      case "log":
        saveLog(logs);
        break;
      case "hardest card":
        showHardestCard(cards, hardestTerms, logs);
        break;
      case "reset stats":
        cards.replaceAll((c, v) -> 0);
        output = "Card statistics has been reset.";
        System.out.println(output);
        logs.add(output);
        break;
    }
  }

  public static void showHardestCard(Map<Card, Integer> cards, List<String> hardestTerms, List<String> logs) {

    if (cards.size() == 0) {
      String output = "There are no cards with errors.";
      System.out.println(output);
      logs.add(output);
    } else {
      int maxValue = (Collections.max(cards.values()));

      if (maxValue == 0) {
        String output = "There are no cards with errors.";
        System.out.println(output);
        logs.add(output);
      } else {
        for (Map.Entry<Card, Integer> entry : cards.entrySet()) {

          if (entry.getValue() == maxValue) {
            hardestTerms.add(entry.getKey().getTerm());
          }
        }
        if (hardestTerms.size() == 1) {
          System.out.println("The hardest card is \"" + hardestTerms.get(0) + "\". You have " + maxValue + " errors answering it.");

        } else {
          System.out.print("The hardest cards are ");

          for (int i = 0; i < hardestTerms.size() - 1; i++) {
            System.out.print("\"" + hardestTerms.get(i) + "\", ");
          }
          System.out.print("\"" + hardestTerms.get(hardestTerms.size() - 1) + "\". ");

          System.out.println("You have " + maxValue + " errors answering them.\n");
        }
      }
      hardestTerms.clear();
    }
  }

  static List<String> createLogList() {

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

      output = "The log has been saved.\n";
      System.out.println(output);
      logs.add(output);
    } catch (IOException e) {
      System.out.printf("An exception occurs %s", e.getMessage());
    }
  }

  static void exportCards(Map<Card, Integer> cards, List<String> logs) {
    String output;
    String input;
    Scanner scanner = new Scanner(System.in);
    output = "File name:";
    System.out.println(output);
    logs.add(output);
    input = scanner.next();
    logs.add(input);
    File file = new File(input);

    try (FileWriter writer = new FileWriter(file)) {
      int counter = 0;
      for (Card card : cards.keySet()) {
        writer.write(card.getTerm() + ";" + card.getDefinition() + "=" + cards.get(card) + "\n");
        counter++;
      }
      output = counter + " cards have been saved.";
      System.out.println(output);
      logs.add(output);
    } catch (IOException e) {
      System.out.printf("An exception occurs %s", e.getMessage());
    }
  }

  static void importCards(Map<Card, Integer> cards, List<String> logs) {
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
        Integer errorCount = Integer.parseInt(entrySet[1]);

        String[] card = entrySet[0].split(";");
        String term = card[0];
        String definition = card[1];

        if (containsTerm(term, cards)) {
          cards.remove(new Card(term, getDefinition(term, cards)));
        }

        cards.put(new Card(term, definition), errorCount);
        counter++;
      }
      output = counter + " cards have been loaded.\n";
      System.out.println(output);
      logs.add(output);
    } catch (FileNotFoundException e) {
      output = "File not found.\n";
      System.out.println(output);
      logs.add(output);
    }

  }

  public static boolean containsTerm(String term, Map<Card, Integer> cards) {
    for (Card card : cards.keySet()) {
      if (term.equals(card.getTerm())) {
        return true;
      }
    }
    return false;
  }

  public static boolean containsDefinition(String definition, Map<Card, Integer> cards) {
    for (Card card : cards.keySet()) {
      if (definition.equals(card.getDefinition())) {
        return true;
      }
    }
    return false;
  }

  public static String getTerm(String definition, Map<Card, Integer> cards) {
    for (Card card : cards.keySet()) {
      if (definition.equals(card.getDefinition())) {
        return card.getTerm();
      }
    }
    return "";
  }

  public static String getDefinition(String term, Map<Card, Integer> cards) {
    for (Card card : cards.keySet()) {
      if (term.equals(card.getTerm())) {
        return card.getDefinition();
      }
    }
    return "";
  }

  static void removeCard(Map<Card, Integer> cards, List<String> logs) {
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

    if (containsTerm(term, cards)) {
      cards.remove(new Card(term, getDefinition(term, cards)));
      output = "The card has been removed.";
    } else {
      output = "Can't remove \"" + term + "\": there is no such card.";
    }
    System.out.println(output);
    logs.add(output);
  }

  static void createCard(Map<Card, Integer> cards, List<String> logs) {
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
    if (containsTerm(term, cards)) {
      output = "The card \"" + term +"\" already exists.";
      System.out.println(output);
      logs.add(output);
      return;
    }

    output = "The definition of the card:";
    System.out.println(output);
    logs.add(output);
    definition = scanner.nextLine();
    input = definition;
    logs.add(input);
    if (containsDefinition(definition, cards)) {
      output = "The definition \"" + definition + "\" already exists.";
      System.out.println(output);
      logs.add(output);
      return;
    }

    Card card = new Card(term, definition);
    cards.put(card, 0);
    output = "The pair (\"" + term + "\":\"" + definition + "\") has been added.";
    System.out.println(output);
    logs.add(output);
  }

  static int determineAskCount(List<String> logs) {
    String output;
    String input;
    Scanner scanner = new Scanner(System.in);
    int askCount;
    output = "How many times to ask?";
    System.out.println(output);
    logs.add(output);
    askCount = scanner.nextInt();
    input = String.valueOf(askCount);
    logs.add(input);
    return askCount;
  }

  static void askForDefinition(int askCount, Map<Card, Integer> cards, List<String> logs) {
    String output;
    String input;

    for (int i = 0; i < askCount; i++) {

      Iterator<Map.Entry<Card, Integer>> iterator = cards.entrySet().iterator();
      Map.Entry<Card, Integer> entry = iterator.next();

      Card c = entry.getKey();

      output = "Print the definition of \"" + c.getTerm() + "\":";
      System.out.println(output);
      logs.add(output);
      String answer = new Scanner(System.in).nextLine();
      input = answer;
      logs.add(input);

      if (answer.equals(c.getDefinition())) {
        output = "Correct answer.";
        System.out.println(output);
        logs.add(output);
      } else {
        if (containsDefinition(answer, cards)) {
          output = "Wrong answer. (The correct one is \"" + c.getDefinition() +
                  "\", you've just written the definition of \"" + getTerm(answer, cards) + "\".";
        } else {
          output = "Wrong answer. The correct one is \"" + c.getDefinition() + "\".";
        }
        System.out.println(output);
        logs.add(output);
        cards.put(c, cards.get(c) + 1);
      }
    }
  }
}

class Card {
  private final String term;
  private final String definition;

  public Card(String term, String definition) {
    this.term = term;
    this.definition = definition;
  }

  public String getTerm() {
    return term;
  }

  public String getDefinition() {
    return definition;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Card card = (Card) o;

    if (!term.equals(card.term)) return false;
    return definition.equals(card.definition);
  }

  @Override
  public int hashCode() {
    int result = term.hashCode();
    result = 31 * result + definition.hashCode();
    return result;
  }
}
