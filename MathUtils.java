public class MathUtils {

    private static double EPSILON = RCReader.getEpsilon(RCReader.read());
    private static String MODE = RCReader.getMode(RCReader.read());

    public static String digitsOf(double x, int prec) {
        if (prec == 0) {
            return "";
        }
        int floor = (int) x;
        return floor + digitsOf(10 * (x - floor), prec - 1);
    }

    public static String dts(double x, int prec) {
        prec = RCReader.getPrecision(RCReader.read());
        if (x < 0) {
            return "-" + dts(-1 * x, prec);
        }
        if (Math.abs((double) ((int) x - x)) < EPSILON && Math.abs(x) < Math.pow(10, prec) && !MODE.equals("scientific")) {
            return "" + Math.round(x);
        } else {
            // If the absolute value of the double is within the range of the double's precision,
            // we can use the double's toString method to get a string representation of the double.
            if (Math.abs(x) < Math.pow(10, prec) && !MODE.equals("scientific")) {
                return x + "";
            }
            // Otherwise, we need to use the log10 method to get the exponent of the double.
            int exp = (int) Math.log10(x);
            if (exp == Integer.MIN_VALUE) {
                // Known bug
                exp = 0;
            }
            // We then use the digitsOf method to get the digits of the double,
            // and then we use the digitsOf method to get the digits of the exponent.
            // We then append the digits of the exponent to the digits of the double.
            String digitsNotSplit = digitsOf(x * Math.pow(10, -exp), prec);
            String digits = digitsNotSplit.substring(0, 1) + "." + digitsNotSplit.substring(1, prec);
            String number = digits + "E" + exp;
            return number;
        }
    }

    public static String removeExcessRoundingErrorFromString(String s) {
        // The goal of this code is to remove the rounding error from a string representation of a number.
        // For example, we want to show "4.8", but it sometimes shows "4.799999999" or "4.80000000001".
        // This code removes the excess digits.

        // We will do this by successively rounding the string's double representation and finding the place where the rounding error occurs.
        // We will then remove the excess digits from the string.
        boolean isNegative = false;
        if (s.startsWith("-")) {
            isNegative = true;
            s = s.substring(1);
        }
        boolean hasE = s.contains("E");
        String[] split = s.split("E");
        String digits = split[0];
        double d = Double.parseDouble(digits);
        int lastRound = 0;
        while (Math.abs(d - Math.round(d)) > EPSILON || s.length() < lastRound) {
            d = Math.round(d);
            lastRound++;
        }
        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append("-");
        }
        sb.append(digits.substring(0, digits.length() - lastRound));
        if (hasE) {
            sb.append("E");
            sb.append(split[1]);
        }
        return sb.toString();
    }
}