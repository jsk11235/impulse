public class ArrayUtils {
    public static boolean contains(String[] arr, String elem) {
        for (String s : arr) {
            if (s.equals(elem)) return true;
        }
        return false;
    }
}
