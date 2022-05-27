import java.util.Stack;

public class MathUtils {
	private static final String[] OPERATORS = { "+", "-", "*", "/", "^", "%" };

	public static double eval (String mathExpression) {
		Stack<String> numbers = new Stack<String>();

		String numberHolder;

		for (int charIndex = mathExpression.length() - 1; charIndex >= 0; charIndex--) {
			String character = mathExpression.substring(charIndex, charIndex + 1);
			if (OPERATORS.contains(character)) {
				String number1 = numbers.pop();
				String number2 = numbers.pop();
				numbers.push(calculate(number1, number2, character));
			} else {
				numberHolder = "";
				while (charIndex >= 0 && !OPERATORS.contains(mathExpression.substring(charIndex, charIndex + 1))) {
					numberHolder = mathExpression.substring(charIndex, charIndex + 1) + numberHolder;
					charIndex--;
				}
				numbers.push(nu
			}
		}
	}

	public static double knockDown(Stack operators, Stack numbers) {
		while (numbers.peek() != "[") {
			String currentChar = numbers.pop();

		}
	}
}
