import java.util.Stack;

public class MathParser {
    private static final char LEFT_PAREN_CHAR = '[';
    private static final char RIGHT_PAREN_CHAR = ']';

    public static double parseMath (String mathString) {
        Stack<Double> values = new Stack<Double>();
        Stack<Character> ops = new Stack<Character>();
        for (int i = 0; i < mathString.length(); i++) {
            char c = mathString.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (c == LEFT_PAREN_CHAR) {
                ops.push(c);
            } else if (c == RIGHT_PAREN_CHAR) {
                while (ops.peek() != LEFT_PAREN_CHAR) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^') {
                while (!ops.empty() && hasPrecedence(c, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(c);
            } else {
                StringBuilder sb = new StringBuilder();
                while (i < mathString.length() && (Character.isDigit(mathString.charAt(i)) || mathString.charAt(i) == '.')) {
                    sb.append(mathString.charAt(i));
                    i++;
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
            }
        }
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    public static boolean hasPrecedence(char op1, char op2) {
        // ^ has precedence over * and /
        // % has precedence over + and -
        if (op2 == LEFT_PAREN_CHAR || op2 == RIGHT_PAREN_CHAR) {
            return false;
        }
        if (op1 == '^') {
            return op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-' || op2 == '%';
        }
        if (op1 == '*' || op1 == '/') {
            return op2 == '+' || op2 == '-' || op2 == '%';
        }
        if (op1 == '+' || op1 == '-') {
            return op2 == '%';
        }
        return false;
    }

    public static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
            case '/':
                return (op == '*') ? a * b : a / b;
            case '%':
                return a % b;
            case '^':
                return Math.pow(a, b);
        }
        return 0;
    }

    public static void main (String[] args) {
        // 243.0
        System.out.println(parseMath("12+2*3^4"));
    }
}