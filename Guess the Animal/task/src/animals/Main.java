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
        System.out.println("Enter an animal:");
        String animal = SC.nextLine();
        System.out.println(request(animal));
        String response = SC.nextLine();
        if (defineAnswer(response))
            System.out.println("You answered: Yes\n");
        else
            System.out.println("You answered: No\n");
        rngGoodbye();
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

    private static String request(String animal) {
        StringBuilder sb = new StringBuilder("Is it ");
        String processed = animal.toLowerCase().replaceAll("((a|an|the) )?(.*)", "$3");
        if (processed.equals("xeme") || processed.matches("^[aeiou].*")) {
            if (processed.equals("unicorn"))
                sb.append("a ");
            else
                sb.append("an ");
        } else
            sb.append("a ");
        sb.append(processed).append('?');
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
