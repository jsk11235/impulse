import java.util.Stack;

public class MathUtils {
	private static final String[] OPERATORS = { "+", "-", "*", "/", "^", "%" };

	public static double operate(String num1,String operator,String num2) throws Exception {
		double dub1 = Double.parseDouble(num1);
		double dub2 = Double.parseDouble(num2);
		if (operator.equals("+"))
			return dub1+dub2;
		else if (operator.equals("-"))
			return dub1-dub2;
		else if (operator.equals("*"))
			return dub1*dub2;
		else if (operator.equals("/"))
			return dub1/dub2;
		else if (operator.equals("^"))
			return Math.pow(dub1,dub2);
		else if (operator.equals("%"))
			return dub1%dub2;
		throw new Exception("Error, invalid operation");
	}

	public static double eval (String mathExpression) {
		Stack<String> chars = new Stack<String>();
		for (int charIndex = mathExpression.length() - 1; charIndex >= 0; charIndex--) {
			String character = mathExpression.substring(charIndex, charIndex + 1);
			if (character.equals("[")) {
				chars = knockDown(chars);
			} else {
				chars.push(character);
			}
		}

		return Double.parseDouble(chars.pop());
	}

	public static Stack<String> knockDown(Stack<String> chars) {
		String num1 = "";
		String num2 = "";
		String operator = null;
		while (!chars.peek().equals("]")) {
			String currentChar = chars.pop();
			if (ArrayUtils.contains(OPERATORS,currentChar))
				operator = currentChar;
			else if (operator==null)
				num1+=currentChar;
			else
				num2+=currentChar;
		}
		chars.pop();
		try {
			chars.push(Double.toString(operate(num1, operator, num2)));
		} catch (Exception e){
			System.out.println("Operation failed");
		}
		return chars;
	}

	public static void main(String[] args){
		System.out.println(eval("[[[3+4]^2]*49]"));
	}
}
