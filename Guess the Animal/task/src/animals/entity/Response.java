package animals.entity;

public class Response {

    private final String verb;
    private String oppositeVerb;
    private final String statement;

    public Response(String input) {
        this.verb = input.replaceFirst("(?i)it (.*?) (.*)[.!?]?\\s?", "$1").toLowerCase();
        initOpposite();
        this.statement = input.replaceFirst("(?i)it (.*?) ([^.!?]*)[.!?]?\\s?", "$2").toLowerCase();
    }

    public Response(String verb, String oppositeVerb, String statement) {
        this.verb = verb;
        this.oppositeVerb = oppositeVerb;
        this.statement = statement;
    }

    private void initOpposite() {
        this.oppositeVerb = switch (verb) {
            case "can" -> "can't";
            case "has" -> "doesn't have";
            case "is" -> "isn't";
            default -> throw new IllegalStateException();
        };
    }

    public Response capitalize() {
        char c = verb.toUpperCase().charAt(0);
        String newVerb = c + verb.substring(1);

        c = oppositeVerb.toUpperCase().charAt(0);
        String newOppositeVerb = c + oppositeVerb.substring(1);

        c = statement.toUpperCase().charAt(0);
        String newStatement = c + statement.substring(1);

        return new Response(newVerb, newOppositeVerb, newStatement);
    }

    public String getVerb() {
        return verb;
    }

    public String getOppositeVerb() {
        return oppositeVerb;
    }

    public String getStatement() {
        return statement;
    }
}
