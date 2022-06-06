package animals;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private final static Scanner SC = new Scanner(System.in);
    private final static Random RNG = new Random();

    public static void main(String[] args) {
        System.out.println(greetingTime());
        System.out.println();
        System.out.println("Enter the first animal:");
        String firstAnimal = SC.nextLine();
        System.out.println("Enter the second animal:");
        String secondAnimal = SC.nextLine();
        String response = null;
        do {
            if (response != null)
                System.out.println("""
                        The examples of a statement:
                         - It can fly
                         - It has horn
                         - It is a mammal""");
            System.out.printf("Specify a fact that distinguishes %s from %s.%n",
                    firstAnimal = withArticle(firstAnimal), secondAnimal = withArticle(secondAnimal));
            System.out.println("The sentence should be of the format: 'It can/has/is ...'.\n");

            response = SC.nextLine();
        } while (!response.matches("(?is)it (can|has|is) .*"));
        fact(firstAnimal, secondAnimal, response);
        rngGoodbye();
    }

    private static void fact(String firstAnimal, String secondAnimal, String response) {
        String definiteFirst = firstAnimal.replaceFirst("a |an ", "The ");
        String definiteSecond = secondAnimal.replaceFirst("a |an ", "The ");
        String responseVerb = response.replaceFirst("(?i)it (.*?) (.*)[.!?]?\\s?", "$1").toLowerCase();
        String oppositeVerb = getOpposite(responseVerb);
        String statement = response.replaceFirst("(?i)it (.*?) ([^.!?]*)[.!?]?\\s?", "$2").toLowerCase();

        System.out.printf("Is it correct for %s?%n", secondAnimal);

        if (defineAnswer(SC.nextLine())) {
            System.out.println("I have learned the following facts about animals:");
            System.out.printf("- %s %s %s.%n", definiteFirst, oppositeVerb, statement);
            System.out.printf("- %s %s %s.%n", definiteSecond, responseVerb, statement);
        } else {
            System.out.println("I have learned the following facts about animals:");
            System.out.printf("- %s %s %s.%n", definiteFirst, responseVerb, statement);
            System.out.printf("- %s %s %s.%n", definiteSecond, oppositeVerb, statement);
        }

        System.out.println("I can distinguish these animals by asking the question:");
        if (responseVerb.equals("has")) {
            System.out.printf("- Does it have %s?%n%n", statement);
        } else {
            System.out.printf("- %s it %s?%n%n", capitalize(responseVerb), statement);
        }
    }

    private static String capitalize(String str) {
        char c = str.charAt(0);
        return Character.toUpperCase(c) + str.substring(1);
    }

    private static String getOpposite(String responseVerb) {
        return switch (responseVerb) {
            case "can" -> "can't";
            case "has" -> "doesn't have";
            case "is" -> "isn't";
            default -> throw new IllegalStateException();
        };
    }

    private static void rngGoodbye() {
        String[] goodbye = new String[]{
                "Bye.", "Goodbye.", "See you next time.", "See you later.", "See ya!", "Later!",
                "Laters!", "Catch you later.", "Have a good one.", "Take it easy.", "Take care."
        };
        System.out.println(goodbye[RNG.nextInt(0, goodbye.length)]);
    }

    private static boolean defineAnswer(String response) {
        String[] hardcodedYes = new String[]{
                "y", "yes", "yeah", "yep", "sure", "right",
                "affirmative", "correct", "indeed", "you bet", "exactly", "you said it"
        };
        String[] hardcodedNo = new String[]{
                "n", "no", "no way", "nah", "nope", "negative", "i don't think so", " yeah no"
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

    private static String withArticle(String animal) {
        StringBuilder sb = new StringBuilder();
        String trimmedInput = animal.trim().toLowerCase();
        if ((trimmedInput.startsWith("a") || trimmedInput.startsWith("an"))
        && trimmedInput.matches("(a|an) (.*)")) {
            sb.append(trimmedInput);
        } else {
            String processed = trimmedInput
                    .replaceAll("(the )?(.*)", "$2");
            if (processed.matches("^[aeiou].*"))
                sb.append("an ").append(processed);
            else
                sb.append("a ").append(processed);
        }
        return sb.toString();
    }

    private static String greetingTime() {
        var lt = LocalTime.now().getHour();
        if (lt >= 5 && lt < 12)
            return "Good morning";
        else if (lt >= 12 && lt < 18)
            return "Good afternoon";
        else
            return "Good evening";
    }
}
