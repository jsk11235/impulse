import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class ProcedureExecutor {
    /*
     * This method is used to execute a string of procedure code for Impulse Language.
     * Example procedure code (use.ipl):
     * use add
     *
     * number res = add: 1, 2
     * print: res
     */

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

        // Split the procedure code into lines
        String[] lines = body.split("\n");
        // For each line
        for (int i = 0; i < lines.length; i++) {
            // Split the line into words
            String[] words = lines[i].split(" ");
            // If the first word is "use"
            if (words[0].equals("use")) {
                // Load the procedure file
                String procedureFile = words[1];
                String procedureCode = loadProcedureFile(procedureFile);
                // Evaluate the procedure code
                eval(procedureCode, args, vars);
            }

            // If the word contains ":"
            if (words[0].contains(":")) {
                // Split the word into parts
                String[] parts = words[0].split(":");
                // Get the procedure name
                String procedureName = parts[0];
                // Get the procedure arguments
                String[] procedureArgs = parts[1].split(",");
                // Get the procedure variables
                String[] procedureVars = words[1].split(",");
                // Get the procedure code
                String procedureCode = words[2];
                // Create a new procedure
                Procedure procedure = new Procedure(procedureName, procedureCode, procedureArgs, procedureVars);
                // Add the procedure to the procedure map
                ProcedureMap.add(procedure);
            }