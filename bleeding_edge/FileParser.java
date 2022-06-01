import java.util.Queue;
import java.util.HashMap;

// Our file parser, which parses files and returns a hashmap containing variable names and values (for use in the interpreter) and a queue of commands to be executed.

public class FileParser {
    private String fileName;
    private String fileContents;
    private String[] lines;
    private String[] variables;
    private Queue<String> commands;

    public FileParser(String fileName, String fileContents) {
        this.fileName = fileName;
        this.fileContents = fileContents;
        this.lines = fileContents.split("\n");
        this.variables = new String[lines.length];
        this.commands = new Queue<String>();
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileContents() {
        return fileContents;
    }

    public String[] getLines() {
        return lines;
    }

    public String[] getVariables() {
        return variables;
    }

    public Queue<String> getCommands() {
        return commands;
    }

    public Object parse() {
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("#")) {
                continue;
            }
            if (line.startsWith("var")) {
                variables[i] = line.substring(4);
            } else {
                commands.enqueue(line);
            }
        }
    }
}