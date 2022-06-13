package animals;

import animals.view.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        game.greetings();
        game.firstLaunch(args);
        while (true) {
            if (!game.start()) break;
        }
        game.persist();
        game.rngGoodbye();
    }
}
