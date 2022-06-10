public class Colors {
    // Provides all the escape codes we might need
    private static final String ESC = "\033[";
    private static final String RESET = ESC + "0m";
    private static final String BOLD = ESC + "1m";
    private static final String ITALIC = ESC + "3m";
    private static final String UNDERLINE = ESC + "4m";
    private static final String BLINK = ESC + "5m";
    private static final String REVERSE = ESC + "7m";
    private static final String HIDDEN = ESC + "8m";
    private static final String BLACK = ESC + "30m";
    private static final String RED = ESC + "31m";
    private static final String GREEN = ESC + "32m";
    private static final String YELLOW = ESC + "33m";
    private static final String BLUE = ESC + "34m";
    private static final String MAGENTA = ESC + "35m";
    private static final String CYAN = ESC + "36m";
    private static final String WHITE = ESC + "37m";
    private static final String BG_BLACK = ESC + "40m";
    private static final String BG_RED = ESC + "41m";
    private static final String BG_GREEN = ESC + "42m";
    private static final String BG_YELLOW = ESC + "43m";
    private static final String BG_BLUE = ESC + "44m";
    private static final String BG_MAGENTA = ESC + "45m";
    private static final String BG_CYAN = ESC + "46m";
    private static final String BG_WHITE = ESC + "47m";
    private static final String BG_RESET = ESC + "49m";

    public static String reset(String str) {
        return RESET + str;
    }

    public static String reset() {
        return RESET;
    }

    public static String bold(String str) {
        return BOLD + str;
    }

    public static String italic(String str) {
        return ITALIC + str;
    }

    public static String underline(String str) {
        return UNDERLINE + str;
    }

    public static String blink(String str) {
        return BLINK + str;
    }

    public static String reverse(String str) {
        return REVERSE + str;
    }

    public static String hidden(String str) {
        return HIDDEN + str;
    }

    public static String black(String str) {
        return BLACK + str;
    }

    public static String red(String str) {
        return RED + str;
    }

    public static String green(String str) {
        return GREEN + str;
    }

    public static String yellow(String str) {
        return YELLOW + str;
    }

    public static String blue(String str) {
        return BLUE + str;
    }

    public static String magenta(String str) {
        return MAGENTA + str;
    }

    public static String cyan(String str) {
        return CYAN + str;
    }

    public static String white(String str) {
        return WHITE + str;
    }

    public static String bgBlack(String str) {
        return BG_BLACK + str;
    }

    public static String bgRed(String str) {
        return BG_RED + str;
    }

    public static String bgGreen(String str) {
        return BG_GREEN + str;
    }

    public static String bgYellow(String str) {
        return BG_YELLOW + str;
    }

    public static String bgBlue(String str) {
        return BG_BLUE + str;
    }

    public static String bgMagenta(String str) {
        return BG_MAGENTA + str;
    }

    public static String bgCyan(String str) {
        return BG_CYAN + str;
    }

    public static String bgWhite(String str) {
        return BG_WHITE + str;
    }

    // To beginning of line
    public static String bol() {
        // Clear the line and then carriage return
        return ESC + "2K" + ESC + "1G";
    }

    // To end of line
    public static String eol() {
        return ESC + "1K";
    }

    // To beginning of screen
    public static String bos() {
        return ESC + "1H";
    }

    // To end of screen
    public static String eos() {
        return ESC + "1F";
    }

    // To specified position
    public static String pos(int x, int y) {
        return ESC + y + ";" + x + "H";
    }

    // Progress bar util
    public static String progress(int progress, int max, String str) {
        int percent = (int) (((double) progress / (double) max) * 100);
        String bar = "";
        for (int i = 0; i < progress; i++) {
            bar += "=";
        }
        for (int i = progress; i < max; i++) {
            bar += " ";
        }
        return RESET + "[" + bar + "] " + percent + "%" + " - " + str;
    }
}