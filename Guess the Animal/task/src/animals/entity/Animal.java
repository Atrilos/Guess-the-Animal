package animals.entity;

public class Animal {

    public static final String A_ARTICLE = "a";
    public static final String AN_ARTICLE = "an";
    public static final String THE_ARTICLE = "the";

    private String value;
    private String article;

    public Animal(String input) {
        init(input);
    }

    public Animal(String value, String article) {
        this.value = value;
        this.article = article;
    }

    private void init(String input) {
        String trimmedInput = input.trim().toLowerCase();
        if ((trimmedInput.startsWith("a") || trimmedInput.startsWith("an"))
                && trimmedInput.matches("(a|an) (.*)")) {
            value = trimmedInput.replaceAll("(a|an) (.*)", "$2");
            article = trimmedInput.replaceAll("(a|an) (.*)", "$1");
        } else {
            String processed = trimmedInput
                    .replaceAll("(the )?(.*)", "$2");
            if (processed.matches("^[aeiou].*")) {
                article = AN_ARTICLE;
            } else {
                article = A_ARTICLE;
            }
            value = processed;
        }
    }

    @Override
    public String toString() {
        return article + " " + value;
    }

    public Animal capitalize() {
        String newArticle = switch (this.article) {
            case A_ARTICLE -> "A";
            case AN_ARTICLE -> "An";
            case THE_ARTICLE -> "The";
            default -> throw new IllegalStateException();
        };

        return new Animal(value, newArticle);
    }

    public String getArticle() {
        return article;
    }

    public Animal withDefiniteArticle() {
        return new Animal(value, THE_ARTICLE);
    }

    public String getQuestion() {
        return "Is it " + article + " " + value + "?";
    }
}
