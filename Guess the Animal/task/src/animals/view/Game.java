package animals.view;

import animals.binaryTree.KnowledgeTree;
import animals.entity.Animal;
import animals.entity.Response;
import animals.persistence.Persistence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private final static Scanner SC = new Scanner(System.in);
    private final static Random RNG = new Random();
    private static KnowledgeTree tree;
    private static String fileName;
    private static ObjectMapper objectMapper = new JsonMapper();

    public boolean start() {
        Animal firstAnimal = null;
        Object next;
        boolean answer;
        boolean isRight = false;

        System.out.print("""
        Let's play a game!
        You think of an animal, and I guess it.
        Press enter when you're ready.
        """);
        if (!SC.nextLine().equals(""))
            throw new RuntimeException();

        next = tree.getRootValue();

        while (true) {
            if (next == null) {
                System.out.println("I give up. What animal do you have in mind?");
                Animal correctAnimal = new Animal(SC.nextLine());

                System.out.printf("Specify a fact that distinguishes %s from %s.%n",
                        firstAnimal, correctAnimal);
                System.out.print("""
                        The sentence should satisfy one of the following templates:
                        - It can ...
                        - It has ...
                        - It is a/an ...
                        """);
                String response = SC.nextLine();
                fact(firstAnimal, correctAnimal, response, isRight);
                break;
            } else {
                if (next instanceof Animal) {
                    firstAnimal = (Animal) next;
                    System.out.println(firstAnimal.getQuestion());
                    answer = defineAnswer(SC.nextLine());
                    if (answer) {
                        System.out.println("Nice!");
                        break;
                    } else {
                        next = tree.getNextValue(false);
                    }
                } else {
                    System.out.println(next);
                    answer = defineAnswer(SC.nextLine());
                    isRight = answer;
                    next = tree.getNextValue(answer);
                }
            }
        }

        System.out.println("\nWould you like to play again?");
        return defineAnswer(SC.nextLine());
    }

    public void greetings() {
        var lt = LocalTime.now().getHour();
        StringBuilder sb = new StringBuilder();

        if (lt >= 5 && lt < 12)
            sb.append("Good morning\n");
        else if (lt >= 12 && lt < 18)
            sb.append("Good afternoon\n");
        else
            sb.append("Good evening\n");

        System.out.println(sb);
    }

    public void firstLaunch(String[] args) {
        fileName = "animals.";
        if (args.length == 2 && args[0].equals("-type")) {
            if (args[1].equals("xml")) {
                fileName += "xml";
                objectMapper = new XmlMapper();
            } else if (args[1].equals("yaml")) {
                fileName += "yaml";
                objectMapper = new YAMLMapper();
            } else {
                fileName += "json";
            }
        } else {
            fileName += "json";
        }

        KnowledgeTree tree = load();

        if (tree == null) {
            System.out.println("I want to learn about animals.");
            System.out.println("Which animal do you like most?");
            Game.tree = new KnowledgeTree(new Animal(SC.nextLine()));
            System.out.println("Wonderful! I've learned so much about animals!");
        } else {
            System.out.println("I know a lot about animals.");
            Game.tree = tree;
        }
    }

    private boolean defineAnswer(String response) {
        String[] hardcodedYes = new String[]{
                "y", "yes", "yeah", "yep", "sure", "right",
                "affirmative", "correct", "indeed", "you bet", "exactly", "you said it"
        };
        String[] hardcodedNo = new String[]{
                "n", "no", "no way", "nah", "nope", "negative", "i don't think so", "yeah no"
        };
        String[] hardcodedClarifyingQuestion = new String[]{
                "I'm not sure I caught you: was it yes or no?",
                "Funny, I still don't understand, is it yes or no?",
                "Oh, it's too complicated for me: just tell me yes or no.",
                "Could you please simply say yes or no?",
                "Oh, no, don't try to confuse me: say yes or no."
        };
        for(;;) {
            String finalResponse = response;
            if (Arrays.stream(hardcodedYes)
                    .anyMatch(s -> finalResponse.trim().toLowerCase().matches(s + "[!.]?")))
                return true;
            else if (Arrays.stream(hardcodedNo)
                    .anyMatch(s -> finalResponse.trim().toLowerCase().matches(s + "[!.]?"))) {
                return false;
            } else {
                System.out.println(hardcodedClarifyingQuestion[RNG.nextInt(0, hardcodedClarifyingQuestion.length)]);
                response = SC.nextLine();
            }
        }
    }

    public void rngGoodbye() {
        String[] goodbye = new String[]{
                "Bye.", "Goodbye.", "See you next time.", "See you later.", "See ya!", "Later!",
                "Laters!", "Catch you later.", "Have a good one.", "Take it easy.", "Take care."
        };
        System.out.println(goodbye[RNG.nextInt(0, goodbye.length)]);
    }

    private void fact(Animal firstAnimal, Animal secondAnimal, String response, boolean isRight) {
        Response responseObject = new Response(response);
        boolean answer;

        System.out.printf("Is the statement correct for %s?%n", secondAnimal);
        answer = defineAnswer(SC.nextLine());

        System.out.println("I have learned the following facts about animals:");
        if (answer) {
            System.out.printf(
                    "- %s %s %s.%n",
                    firstAnimal.withDefiniteArticle().capitalize(),
                    responseObject.getOppositeVerb(),
                    responseObject.getStatement()
            );
            System.out.printf(
                    "- %s %s %s.%n",
                    secondAnimal.withDefiniteArticle().capitalize(),
                    responseObject.getVerb(),
                    responseObject.getStatement()
            );
        } else {
            System.out.printf(
                    "- %s %s %s.%n",
                    firstAnimal.withDefiniteArticle().capitalize(),
                    responseObject.getVerb(),
                    responseObject.getStatement()
            );
            System.out.printf(
                    "- %s %s %s.%n",
                    secondAnimal.withDefiniteArticle().capitalize(),
                    responseObject.getOppositeVerb(),
                    responseObject.getStatement()
            );
        }

        System.out.println("I can distinguish these animals by asking the question:");
        String question;

        if (responseObject.getVerb().equals("has")) {
            question = String.format("Does it have %s?", responseObject.getStatement());
            System.out.printf("- %s%n%n", question);
        } else {
            question = String.format("%s it %s?", responseObject.capitalize().getVerb(), responseObject.getStatement());
            System.out.printf("- %s%n%n", question);
        }
        tree.add(question, secondAnimal, !answer, isRight);
    }

    public void persist() {
        try {
            Persistence.save(tree, fileName, objectMapper);
        } catch (IOException ignored) {
        }
    }

    public KnowledgeTree load() {
        try {
            return Persistence.load(fileName, objectMapper);
        } catch (IOException e) {
            return null;
        }
    }
}
