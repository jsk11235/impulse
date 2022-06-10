import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;

public class REPL {
    // The Impulse REPL (Read Evaluate Print Loop)
    // If no file is specified, the REPL will be started.

    static int paramNum = 0;
    static boolean runnable = true;
    static int ifs = 0;
    static boolean isOpenComment = false;
    static String fileName;
    static String filePath;
    static ArrayList<Double> vars = new ArrayList<Double>();
    static ArrayList<String> keys = new ArrayList<String>();
    static ArrayList<String> src = new ArrayList<String>();
    static HashMap<String, Procedure> childProcedures = new HashMap<String, Procedure>();
    static int lineNum = 0;
    static int colNum = 0;
    static int prec = 10;

    public static double doMath(String math) {
        String newMath = StringUtils.removeSpaces(math);
        for (int i = 0; i < vars.size(); i++)
            newMath = newMath.replaceAll(keys.get(i), MathUtils.dts(vars.get(i), prec));
        try {
            return Double.parseDouble(math);
        } catch (Exception e) {

        }
        return MathParser.parseMath(newMath);
    }

    public static double runReturn(String line) {
        if (line == "return") {
            System.out.println(Colors.bold(Colors.green("Script exited without error.")));
            System.exit(0);
        }
        String ref = line.substring(6);
        for (int i = 0; i < keys.size(); i++) {
            if (ref.equals(keys.get(i))) return vars.get(i);
        }

        System.out.println(doMath(ref));
        System.out.println(Colors.bold(Colors.green("Script exited without error.")));
        System.exit(0);
        return 0;
    }

    public static void runParam(String line, double value) {
        String name = line.substring(5);
        keys.add(name);
        vars.add(value);
    }

    public static void runVar(String line) {
        int equalsIdx = line.indexOf("=");
        String name = line.substring(3, equalsIdx);
        String ref = line.substring(equalsIdx + 1);
        if (keys.contains(name)) System.out.println("Variable already declared");
        else {
            keys.add(name);
            vars.add(doMath(ref));
        }
    }

    public static void runPrint(String line) {
        String ref = line.substring(5);
        System.out.println(MathUtils.dts(doMath(ref), prec));
    }

    public static void runDisplay(String line) {
        // Check if the command actually is displayln
        if (line.substring(7, 9).equals("ln")) {
            String ref = line.substring(10);
            System.out.println(ref.replaceAll("\"", ""));
        } else {
            String ref = line.substring(8);
            System.out.print(ref.replaceAll("\"", ""));
        }
    }

    public static boolean runIf(String line) {
        String ref = line.substring(2);
        for (int i = 0; i < vars.size(); i++)
            ref = ref.replaceAll(keys.get(i), MathUtils.dts(vars.get(i), 20));
        return BooleanParser.parseBool(ref);
    }

    public static void runRes(String line) throws FileNotFoundException {
        for (int i = 0; i < vars.size(); i++)
            line = line.replaceAll(keys.get(i), MathUtils.dts(vars.get(i), 20));
        int equalsIdx = line.indexOf("=");
        int colonIdx = line.indexOf(":");
        String varName = line.substring(3, equalsIdx);
        String procName = line.substring(equalsIdx + 1, colonIdx);
        String[] strungParams = line.substring(colonIdx + 1).split(",");
        double[] newParams = new double[strungParams.length];
        int idx = 0;
        for (String s : strungParams) {
            newParams[idx] = doMath(s);
            idx++;
        }
        if (childProcedures.containsKey(procName)) {
            double res = childProcedures.get(procName).run(newParams);
            vars.add(res);
            keys.add(varName);
        } else {
            Procedure proc = new Procedure(procName, true);
            childProcedures.put(procName, proc);
            double res = proc.run(newParams);
            vars.add(res);
            keys.add(varName);
        }
    }

    public static void runPrompt(String line) {
        // Prompt the user for input. The first variable will be the var name to store the input in, and the second variable will be the prompt.
        String ref = line.substring(7);
        String varName = ref.split(",")[0];
        String prompt = ref.split(",")[1];
        System.out.println(Colors.italic(prompt.replaceAll("\"", "")) + ": " + Colors.reset());
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine().replace("\n", "");
        vars.add(doMath(input));
        keys.add(varName);
    }

    public static void attemptImport(String line) {
        // Import will import a procedure from a file that could be outside of the current directory.
        // An import statement will look like this: import "filename" as "procName"
        // After this statement, the procedure that is being added can be called with the name "procName", like procedures within the directory.

        // Get the directory of the procedure calling the import statement.
        String ref = line.substring(6);
        String[] split = ref.split("\"");
        String fileName = split[1];
        String procName = split[3];
        try {
            Procedure proc = new Procedure(filePath + File.separator + fileName, true);
            childProcedures.put(procName, proc);
        } catch (FileNotFoundException e) {
            new ImpulseError("ProcedureNotFound", "The procedure in file " + fileName + " was not found.", -1, -1, null).exit();
        }
    }


    public static void execLine(String line) {
        try {
            // We are starting lineNum at 0 but line numbers actually start at 1
            lineNum++;
            colNum = 0;
            if (line.equals("over")) {
                if (ifs == 0) {
                    runnable = true;
                    return;
                } else {
                    ifs--;
                }
            }
            if (runnable) {
                if (line.startsWith("$")) {
                    // It's a comment
                    return;
                } else if (line.startsWith("[$")) {
                    // Multi-line comment opening
                    isOpenComment = true;
                    return;
                } else if (line.endsWith("$]") && isOpenComment) {
                    // Multi-line comment closing
                    isOpenComment = false;
                    return;
                } else if (line.startsWith("prompt")) {
                    colNum += 6;
                    runPrompt(line);
                } else if (line.startsWith("display")) {
                    colNum += 7;
                    runDisplay(line);
                } else {
                    line = StringUtils.removeSpaces(line);
                    if (line.startsWith("return")) {
                        colNum += 6;
                        runReturn(line);
                    } else if (line.startsWith("param")) {
                        colNum += 5;
                        System.out.println(Colors.bold(Colors.red("You cannot use param in the REPL.")) + Colors.reset());
                        return;
                    } else if (line.startsWith("import")) {
                        colNum += 6;
                        attemptImport(line);
                    } else if (line.startsWith("print")) {
                        colNum += 5;
                        runPrint(line);
                    } else if (line.startsWith("var")) {
                        colNum += 3;
                        runVar(line);
                    } else if (line.startsWith("res")) {
                        colNum += 3;
                        runRes(line);
                    } else if (line.startsWith("if")) {
                        if (!runIf(line)) runnable = false;
                    } else if (line.startsWith("exit")) {
                        colNum += 5;
                        System.out.println(Colors.bold(Colors.yellow("Impulse exited prematurely.")));
                        System.exit(0);
                    } else if (!line.equals("over") && !isOpenComment) {
                        try {
                            double res = doMath(line);
                            System.out.println(res);
                        } catch (Exception e) {
                            new ImpulseError("CompileError", "I don't know what " + line.split(" ")[0] + " is.", lineNum, -1, null).print();
                        }
                    }
                }
            } else {
                if (line.startsWith("if")) {
                    ifs++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            new ImpulseError("RunError", "Something unexpected happened.", -1, -1, null).exit();
        }
    }

    public static void repl() {
        // Collect lines until the user sends "run", and if they send "quit", then exit the REPL.
        System.out.println(Colors.bold("Impulse REPL ") + Colors.reset() + Colors.italic("v6.0.0") + Colors.reset());
        System.out.println("Type \"quit\" to exit the REPL.");
        System.out.println("If you want to learn how to use Impulse, please refer to the documentation in the docs folder.");
        String line = "";
        ArrayList<String> lines = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (!line.equals("quit")) {
            System.out.print("> ");
            line = scanner.nextLine();
            lines.add(line);
            if (line.equals("run")) {
                break;
            }
            if (line.equals("quit")) {
                System.exit(0);
            }
            execLine(line);
        }
        // Create a temporary file to store the program.
    }
}