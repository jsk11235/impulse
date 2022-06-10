public class PrettyPrint {
    private static final String[] KEYWORDS_PINK = {
            "if", "over", "param", "return", "var", "res"
    };
    private static final String[] KEYWORDS_RED = {
            "import", "as"
    };
    private static final String[] KEYWORDS_YELLOW = {
            "print", "display", "displayln", "prompt"
    };
    private static final String[] KEYWORDS_BG_GREEN = {
            "\\[","\\]"
    };
    private static final String[] KEYWORDS_BLUE = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    public static String formatLine (String line, String[] varNames) {
        // Color all numbers blue
        line = line.replaceAll("\\d+", Colors.blue("$0") + Colors.reset());
        for (String keyword : KEYWORDS_PINK) {
            line = line.replaceAll(keyword, Colors.magenta(keyword)  + Colors.reset());
        }
        for (String keyword1 : KEYWORDS_YELLOW) {
            line = line.replaceAll(keyword1, Colors.yellow(keyword1) + Colors.reset());
        }
        for (String keyword4 : KEYWORDS_RED) {
            line = line.replaceAll(keyword4, Colors.red(keyword4) + Colors.reset());
        }
        for (String varName : varNames) {
            line = line.replaceAll(varName, Colors.green(varName) + Colors.reset());
        }

        return line;
    }

    public static String formatLine (String line) {
        // Color all numbers blue
        line = line.replaceAll("\\d+", Colors.blue("$0") + Colors.reset());
        for (String keyword : KEYWORDS_PINK) {
            line = line.replaceAll(keyword, Colors.magenta(keyword)  + Colors.reset());
        }
        for (String keyword1 : KEYWORDS_YELLOW) {
            line = line.replaceAll(keyword1, Colors.yellow(keyword1) + Colors.reset());
        }
        for (String keyword4 : KEYWORDS_RED) {
            line = line.replaceAll(keyword4, Colors.red(keyword4) + Colors.reset());
        }

        return line;
    }

    public static String[] formatLines (String[] lines) {
        String[] res = new String[lines.length];
        for (int x = 0; x < res.length; x++) {
            res[x] = formatLine(lines[x]);
        }
        return res;
    }

    public static void main (String[] args) {
        // Test code highlighting
        String[] code = {
                "import \"test\" as \"module\"",
                "param num",
                "var times = num * 2",
                "res x = procedure: 2, 5",
                "if [times < x]",
                "  return 3",
                "over",
                "return 1"
        };

        for (String line : code) {
            System.out.println(line);
        }
        System.out.println("========");
        String[] formatted = formatLines(code);
        for (String line2 : formatted) {
            System.out.println(line2);
        }
    }
}