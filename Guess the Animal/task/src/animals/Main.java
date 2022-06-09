package animals;

import animals.view.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        game.greetings();
        game.firstLaunch();
        while (true) {
            if (!game.start()) break;
        }
        game.rngGoodbye();
    }
}
