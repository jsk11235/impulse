public class Procedure {
    private String name;
    private String body;
    private String[] args;
    private String[] vars;

    public Procedure(String name, String body, String[] args, String[] vars) {
        this.name = name;
        this.body = body;
        this.args = args;
        this.vars = vars;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String[] getArgs() {
        return args;
    }

    public String[] getVars() {
        return vars;
    }

    public String toString() {
        return name + "(" + String.join(", ", args) + ")";
    }

    public static Procedure parse(String s) {
        String[] parts = s.split("\\(");
        String name = parts[0];
        String[] args = parts[1].split("\\)")[0].split(", ");
        String body = parts[1].split("\\)")[1];
        String[] vars = body.split("\\{")[1].split("\\}")[0].split(", ");
        return new Procedure(name, body, args, vars);
    }

    public static String[] parseArgs(String s) {
        return s.split("\\(")[1].split("\\)")[0].split(", ");
    }

    public static String[] parseVars(String s) {
        return s.split("\\{")[1].split("\\}")[0].split(", ");
    }

    public static String parseBody(String s) {
        return s.split("\\{")[1].split("\\}")[0];
    }

    public static String parseName(String s) {
        return s.split("\\(")[0];
    }

    // just an edit to get another commit message in

    public void run () {
        ProcedureEvaluator.eval(body, args, vars);
    }
}