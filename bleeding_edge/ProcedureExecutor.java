import java.io.BufferedReader;
import java.io.FileReader;

public class ProcedureExecutor {
    /*
     * This method is used to execute a string of procedure code for Impulse Language.
     * Example procedure code (use.ipl):
     * use add
     *
     * number res = add: 1, 2
     * print: res
     */

    private static final String[] LIBRARIES = {"io", "number"}

    public static String loadProcedureFile (String fileName) {
        String fileContents = "";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents += line + "\n";
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents;
    }

    public static void eval (body, args, vars) {
        // This is about to be the longest Java code I've ever written.
        // I'm sorry.


}