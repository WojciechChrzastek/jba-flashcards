package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    while (0 != 1) {
      String action = askForAction();
      executeAction(action, cards);
    }
  }

  static String askForAction() {
    List<String> actions = Arrays.asList("add", "remove", "import", "export", "ask", "l", "exit");
    boolean isAction;
    String action;
    Scanner scanner = new Scanner(System.in);
    System.out.println("Input the action (add, remove, import, export, ask, l, exit):");
    do {
      action = scanner.next();
      isAction = actions.contains(action);
      if (!isAction) {
        System.out.println("Please input a valid action (add, remove, import, export, ask, exit):");
      }
    }
    while (!isAction);
    return action;
  }

  static void executeAction(String action, Map<String, String> cards) {
    switch (action) {
      case "add":
        createCard(cards);
        break;
      case "remove":
        removeCard(cards);
        break;
      case "import":
        importCards(cards);
        break;
      case "export":
        exportCards(cards);
        break;
      case "ask":
        if (cards.size() > 0) {
          askForDefinition(determineAskCount(cards), cards);
        } else {
          System.out.println("The cards set is empty!\n");
        }
        break;
      case "l":
        System.out.println(cards);
        break;
      case "exit":
        System.out.println("Bye bye!");
        System.exit(0);
        break;
    }
  }

  static void exportCards(Map<String, String> cards) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("File name:");
    File file = new File(scanner.next());

    try (FileWriter writer = new FileWriter(file)) {
      int counter = 0;
      for (var entry : cards.entrySet()) {
        writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
        counter++;
      }
      System.out.println(counter + " cards have been saved.");
    } catch (IOException e) {
      System.out.printf("An exception occurs %s", e.getMessage());
    }
  }

  static void importCards(Map<String, String> cards) {
    Scanner scannerInput = new Scanner(System.in);
    System.out.println("File name:");
    String path = scannerInput.next();
    File file = new File(path);
    int counter = 0;

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String entrySetString = scanner.nextLine();
        String[] entrySet = entrySetString.split("=");
        cards.put(entrySet[0], entrySet[1]);
        counter++;
      }
      System.out.println(counter + " cards have been loaded.");
    } catch (FileNotFoundException e) {
      System.out.println("File not found.\n");
    }

  }

  static Map<String, String> createCardMap() {
    return new HashMap<>();
  }

  static void removeCard(Map<String, String> cards) {
    Scanner scanner = new Scanner(System.in);
    String term;

    System.out.println("The card:");
    term = scanner.nextLine();
    if (cards.containsKey(term)) {
      cards.remove(term);
      System.out.println("The card has been removed.\n");
    } else {
      System.out.printf("Can't remove \"%s\": there is no such card.\n\n", term);
    }
  }

  static void createCard(Map<String, String> cards) {
    Scanner scanner = new Scanner(System.in);
    String term;
    String definition;

    System.out.println("The card:");
    term = scanner.nextLine();
    if (cards.containsKey(term)) {
      System.out.printf("The card \"%s\" already exists.\n\n", term);
      return;
    }

    System.out.println("The definition of the card:");
    definition = scanner.nextLine();
    if (cards.containsValue(definition)) {
      System.out.printf("The definition \"%s\" already exists.\n\n", definition);
      return;
    }

    cards.putIfAbsent(term, definition);
    System.out.printf("The pair (\"%s\":\"%s\") has been added.\n\n", term, definition);
  }

  static int determineAskCount(Map<String, String> cards) {
    Scanner scanner = new Scanner(System.in);
    int askCount;
    System.out.println("How many times to ask?");
//    while (scanner.hasNext()) {
//      if (scanner.hasNextInt()) {
    askCount = scanner.nextInt();
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

  static void askForDefinition(int askCount, Map<String, String> cards) {

    for (int i = 0; i < askCount; i++) {
      Map.Entry<String, String> entry = cards.entrySet().iterator().next();
      System.out.println("Print the definition of \"" + entry.getKey() + "\":");
      String answer = new Scanner(System.in).nextLine();

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
