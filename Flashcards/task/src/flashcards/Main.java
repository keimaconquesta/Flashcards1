package flashcards;

import java.io.*;
import java.util.*;

public class Main {

    static List<String> argsList;
    static List<String> log = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static boolean exit = false;

    static List<String> cards = new ArrayList<>();
    static List<String> definitions = new ArrayList<>();
    static List<Integer> mistakeCounts = new ArrayList<>();
    static List<String> hardestCards = new ArrayList<>();
    static Map<String, Integer> cardsAndMistakes;
    static String action = "", answer = "", card = "", definition = "", fileName = "";
    static int mistakeCount = 0, cardsRead = 0, questions = 0, hardestCardMistakes = 0;;
    static Random random = new Random();
    static File file;

    public static void main(String[] args) {
        argsList = new ArrayList<>(Arrays.asList(args));
        if (argsList.contains("-import")) {
            importMain();
        }

        while (!exit) {
            if (!action.equals(""))
                output("");
            output("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            action = scanner.nextLine();

            switch (action) {
                case "exit":
                    output("Bye bye!");
                    exit = true;
                    break;

                case "add":
                    addAction();
                    break;

                case "remove":
                    removeAction();
                    break;

                case "import":
                    importAction();
                    break;

                case "export":
                    exportAction();
                    break;

                case "ask":
                    askAction();
                    break;

                case "log":
                    logAction();
                    break;

                case "hardest card":
                    hardestCardAction();
                    break;

                case "reset stats":
                    resetStatsAction();
                    break;
            }
        }

        if (argsList.contains("-export")) {
            exportMain();
        }
    }

    static void importGlobal() {
        try (Scanner reader = new Scanner(file)) {
            cardsRead = 0;
            while (reader.hasNextLine()) {
                card = reader.nextLine();

                if (reader.hasNextLine()) {
                    definition = reader.nextLine();

                    if (reader.hasNextLine()) {
                        mistakeCount = Integer.parseInt(reader.nextLine());

                        if (cards.contains(card)) {
                            definitions.set(cards.indexOf(card), definition);
                            mistakeCounts.set(cards.indexOf(card), mistakeCount);
                        } else {
                            cards.add(card);
                            definitions.add(definition);
                            mistakeCounts.add(mistakeCount);
                        }
                    }
                }
                cardsRead++;
            }

            output(cardsRead + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            output("File not found.");
        }
    }

    static void exportGlobal() {
        try (Writer writer = new PrintWriter(file)) {
            cardsRead = 0;
            for (int i = 0; i < cards.size(); i++) {
                writer.write(cards.get(i) + "\n"
                        + definitions.get(i) + "\n"
                        + mistakeCounts.get(i));

                if (i < cards.size() - 1) {
                    writer.write("\n");
                }

                cardsRead++;
            }

            output(cardsRead + " cards have been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void importMain() {
        fileName = argsList.get(argsList.indexOf("-import") + 1);
        file = new File(fileName);
        importGlobal();
    }

    static void exportMain() {
        fileName = argsList.get(argsList.indexOf("-export") + 1);
        file = new File(fileName);
        exportGlobal();
    }

    static void addAction() {
        output("The card:");
        input(card = scanner.nextLine());

        if (cards.contains(card)) {
            output("The card \"" + card + "\" already exists.");
        } else {
            output("The definition of the card:");
            input(definition = scanner.nextLine());

            if (definitions.contains(definition)) {
                output("The definition \"" + definition + "\" already exists.");
            } else {
                cards.add(card);
                definitions.add(definition);
                mistakeCounts.add(0);
                output("The pair (\"" + card + "\":\"" + definition + "\") has been added.");
            }
        }
    }

    static void removeAction() {
        output("The card:");
        input(card = scanner.nextLine());

        if (cards.contains(card)) {
            mistakeCounts.remove(cards.indexOf(card));
            definitions.remove(cards.indexOf(card));
            cards.remove(card);

            if (hardestCards.contains(card)) {
                hardestCards.remove(card);

                if (hardestCards.isEmpty()) {
                    hardestCardMistakes = 0;
                }
            }
            output("The card has been removed.");
        } else {
            output("Can't remove \"" + card + "\": there is no such card.");
        }
    }

    static void importAction() {
        output("File name:");
        input(fileName = scanner.nextLine());
        file = new File(fileName);
        importGlobal();
    }

    static void exportAction() {
        output("File name:");
        input(fileName = scanner.nextLine());
        file = new File(fileName);
        exportGlobal();
    }

    static void askAction() {
        output("How many times to ask?");
        input(String.valueOf(questions = Integer.parseInt(scanner.nextLine())));

        for (int i = 0; i < questions; i++) {
            card = cards.get(random.nextInt(cards.size()));
            output("Print the definition of \"" + card + "\":");
            input(answer = scanner.nextLine());

            if (answer.equals(definitions.get(cards.indexOf(card)))) {
                output("Correct answer.");
            } else {
                if (definitions.contains(answer)) {
                    output("Wrong answer. The correct one is \""
                            + definitions.get(cards.indexOf(card))
                            + "\", you've just written the definition of \""
                            + cards.get(definitions.indexOf(answer))
                            + "\".");
                } else {
                    output("Wrong answer. The correct one is \""
                            + definitions.get(cards.indexOf(card)) + "\".");
                }
                mistakeCounts.set(cards.indexOf(card), mistakeCounts.get(cards.indexOf(card)) + 1);
            }
        }
    }

    static void logAction() {
        output("File name:");
        input(fileName = scanner.nextLine());
        file = new File(fileName);

        try (Writer writer = new PrintWriter(file)) {
            for (String text : log) {
                writer.write(text);

                if (log.indexOf(text) < log.size() - 1) {
                    writer.write("\n");
                }
            }
            output("The log has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void hardestCardAction() {
        cardsAndMistakes = new LinkedHashMap<>();

        for (int i = 0; i < cards.size(); i++) {
            cardsAndMistakes.put(cards.get(i), mistakeCounts.get(i));
        }

        for (Map.Entry<String, Integer> entry : cardsAndMistakes.entrySet()) {
            if (entry.getValue() >= hardestCardMistakes && entry.getValue() > 0) {
                if (entry.getValue() > hardestCardMistakes) {
                    hardestCards.clear();
                    hardestCardMistakes = entry.getValue();
                }

                hardestCards.add(entry.getKey());
            }
        }

        if (hardestCardMistakes == 0 || hardestCards.isEmpty()) {
            output("There are no cards with errors.");
        } else {
            if (hardestCards.size() == 1) {
                output("The hardest card is \"" + hardestCards.get(0)
                        + "\". You have " + hardestCardMistakes + " errors answering it.");
            } else {
                output("The hardest cards are \"" + hardestCards.toString()
                        .replace("[", "").replace("]", "")
                        .replace(" ", "").replace(",", "\", \"")
                        + "\". You have " + hardestCardMistakes + " errors answering them.");
            }
        }
    }

    static void resetStatsAction() {
        for (int i = 0; i < mistakeCounts.size(); i++) {
            mistakeCounts.set(i, 0);
        }
        hardestCards.clear();
        hardestCardMistakes = 0;
        output("Card statistics has been reset.");
    }

    //INPUT - OUTPUT METHODS
    static void input(String input) {
        log.add(input);
    }

    static void output(String output) {
        System.out.println(output);
        input(output);
    }
}