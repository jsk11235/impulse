import java.util.Stack;

public class BooleanParser {
    private static final char LEFT_PAREN_CHAR = '[';
    private static final char RIGHT_PAREN_CHAR = ']';

    public static boolean isOp(char c) {
        // Include XOR so we can create a rudimentary not operator
        return c == '|' || c == '&' || c == '=' || c == '>' || c == '<' || c == '[' || c == ']' || c == '#';
    }

    public static boolean parseBool(String boolString) {
        if (boolString.equals("true") || boolString.equals("false")) return Boolean.parseBoolean(boolString);
        Stack<String> values = new Stack<String>();
        Stack<Character> ops = new Stack<Character>();
        for (int i = 0; i < boolString.length(); i++) {
            char c = boolString.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (c == LEFT_PAREN_CHAR) {
                ops.push(c);
            } else if (c == RIGHT_PAREN_CHAR) {
                while (ops.peek() != LEFT_PAREN_CHAR) {
                    values.push(Boolean.toString(applyOp(ops.pop(), values.pop(), values.pop())));
                }
                ops.pop();
            } else if (isOp(c)) {
                while (!ops.empty() && hasPrecedence(ops.peek(), c)) {
                    values.push(Boolean.toString(applyOp(ops.pop(), values.pop(), values.pop())));
                }
                ops.push(c);
            } else {
                StringBuilder sb = new StringBuilder();
                while (i < boolString.length() && !isOp(boolString.charAt(i))) {
                    sb.append(boolString.charAt(i));
                    i++;
                }
                i--;
                values.push(sb.toString());
            }
        }
        while (!ops.empty()) {
            values.push(Boolean.toString(applyOp(ops.pop(), values.pop(), values.pop())));
        }
        return Boolean.parseBoolean(values.pop());
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == LEFT_PAREN_CHAR || op2 == RIGHT_PAREN_CHAR) {
            return false;
        }
        return op1 == '>' || op1 == '<' || op1 == '=';
    }

    public static boolean applyOp(char op, String b, String a) {
        if (a.equals("true") || a.equals("false")) return applyOp(op, Boolean.parseBoolean(b), Boolean.parseBoolean(a));
        else return applyOp(op, Double.parseDouble(b), Double.parseDouble(a));
    }

    // But what if we wanted to do bitwise operations?
    public static boolean applyOp(char op, boolean b, boolean a) {
        switch (op) {
            case '|':
                return a || b;
            case '&':
                return a && b;
            case '#':
                // XOR
                return a ^ b;
        }
        return false;
    }

    public static boolean applyOp(char op, double b, double a) {
        switch (op) {
            case '>':
                return a > b;
            case '<':
                return a < b;
            case '=':
                return a == b;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(parseBool("5<4|5=4"));
    }
}