import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
// Hashmap
import java.util.HashMap;

public class Procedure {
    String fileName;
    String filePath;
    ArrayList<Double> vars;
    ArrayList<String> keys;
    ArrayList<String> src;
    HashMap<String, Procedure> childProcedures = new HashMap<String, Procedure>();
    int lineNum = 0;
    int colNum = 0;
    int prec = 10;

    public Procedure(String name) throws FileNotFoundException {
        fileName = name;
        src = new ArrayList<>();
        vars = new ArrayList<>();
        keys = new ArrayList<>();
        File srcFile = new File(name);
        Scanner reader = new Scanner(srcFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (StringUtils.removeSpaces(line).length() > 0) {
                src.add(line.trim());
            }
        }
        reader.close();
        prec = RCReader.getPrecision(RCReader.read());
        // Store path
        filePath = srcFile.getAbsolutePath().substring(0, srcFile.getAbsolutePath().lastIndexOf(File.separator));
    }


    public static void main(String[] args) throws FileNotFoundException {
        double[] params = null;
        params = getParams(args);
        int argNum = 0;
        String procName = "";
        for (String arg : args) {
            if (argNum == 0) procName = arg;
            else {
                try {
                    params[argNum - 1] = Double.parseDouble(arg);
                } catch (Exception e) {
                    new ImpulseError("MissingArgument", "No argument was provided for " + arg + " when one was expected.", -1, -1, procName).exit();
                }
            }
            argNum++;
        }
        Procedure proc = null;
        proc = getProcedure(procName);
        proc.run(params);
    }

    private static Procedure getProcedure(String procName) {
        Procedure proc = null;
        try {
            proc = new Procedure(procName);
        } catch (FileNotFoundException e) {
            new ImpulseError("FileNotFound", "The file " + procName + " was not found.", -1, -1, null).exit();
        }
        return proc;
    }

    private static double[] getParams(String[] args) {
        double[] params = null;
        try {
            params = new double[args.length - 1];
        } catch (Exception e) {
            new ImpulseError("NoFile", "You must provide a file to run.", -1, -1, null).exit();
        }
        return params;
    }

    public String toString() {
        return "Procedure{" + "vars=" + vars + ", keys=" + keys + ", src=" + src + '}';
    }

    public double doMath(String math) {
        String newMath = StringUtils.removeSpaces(math);
        for (int i = 0; i < vars.size(); i++)
            newMath = newMath.replaceAll(keys.get(i), MathUtils.dts(vars.get(i), 20));
        try {
            return Double.parseDouble(math);
        } catch (Exception e) {

        }
        return MathParser.parseMath(newMath);
    }

    public double runReturn(String line) {
        String ref = line.substring(6);
        for (int i = 0; i < keys.size(); i++) {
            if (ref.equals(keys.get(i))) return vars.get(i);
        }
        return doMath(ref);
    }

    public void runParam(String line, double value) {
        String name = line.substring(5);
        keys.add(name);
        vars.add(value);
    }

    public void runVar(String line) {
        int equalsIdx = line.indexOf("=");
        String name = line.substring(3, equalsIdx);
        String ref = line.substring(equalsIdx + 1);
        if (keys.contains(name)) System.out.println("Variable already declared");
        else {
            keys.add(name);
            vars.add(doMath(ref));
        }
    }

    public void runPrint(String line) {
        String ref = line.substring(5);
        System.out.println(MathUtils.dts(doMath(ref), prec));
    }

    public void runDisplay(String line) {
        // Check if the command actually is displayln
        if (line.substring(7,9).equals("ln")) {
            String ref = line.substring(10);
            System.out.println(ref.replaceAll("\"", ""));
        } else {
            String ref = line.substring(8);
            System.out.print(ref.replaceAll("\"", ""));
        }
    }

    public boolean runIf(String line) {
        String ref = line.substring(2);
        for (int i = 0; i < vars.size(); i++)
            ref = ref.replaceAll(keys.get(i), MathUtils.dts(vars.get(i), 20));
        return BooleanParser.parseBool(ref);
    }

    public void runRes(String line) throws FileNotFoundException {
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
            Procedure proc = new Procedure(procName);
            childProcedures.put(procName, proc);
            double res = proc.run(newParams);
            vars.add(res);
            keys.add(varName);
        }
    }

    public void runPrompt(String line) {
        // Prompt the user for input. The first variable will be the var name to store the input in, and the second variable will be the prompt.
        String ref = line.substring(7);
        String varName = ref.split(",")[0];
        String prompt = ref.split(",")[1];
        System.out.println(Colors.italic(prompt.replaceAll("\"","")) + ": " + Colors.reset());
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine().replace("\n", "");
        vars.add(doMath(input));
        keys.add(varName);
    }

    public void attemptImport (String line) {
        // Import will import a procedure from a file that could be outside of the current directory.
        // An import statement will look like this: import "filename" as "procName"
        // After this statement, the procedure that is being added can be called with the name "procName", like procedures within the directory.

        // Get the directory of the procedure calling the import statement.
        String ref = line.substring(6);
        String[] split = ref.split("\"");
        String fileName = split[1];
        String procName = split[3];
        try {
            Procedure proc = new Procedure(filePath + File.separator + fileName);
            childProcedures.put(procName, proc);
        } catch (FileNotFoundException e) {
            new ImpulseError("ProcedureNotFound", "The procedure in file " + fileName + " was not found.", -1, -1, null).exit();
        }
    }

    public double run(double[] params) throws FileNotFoundException {
        int paramNum = 0;
        boolean runnable = true;
        int ifs = 0;
        boolean isOpenComment = false;

        for (String line : src) {
            try {
                // We are starting lineNum at 0 but line numbers actually start at 1
                lineNum++;
                colNum = 0;
                if (line.equals("over")) {
                    if (ifs == 0) {
                        runnable = true;
                        continue;
                    } else {
                        ifs--;
                    }
                }
                if (runnable) {
                    if (line.startsWith("$")) {
                        // It's a comment
                        continue;
                    } else if (line.startsWith("[$")) {
                        // Multi-line comment opening
                        isOpenComment = true;
                        continue;
                    } else if (line.endsWith("$]") && isOpenComment) {
                        // Multi-line comment closing
                        isOpenComment = false;
                        continue;
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
                            return attemptReturn(line);
                        } else if (line.startsWith("param")) {
                            colNum += 5;
                            paramNum = attemptSetParam(params, paramNum, line);
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
                        } else if (!line.equals("over") && !isOpenComment) {
                            new ImpulseError("CompileError", "I don't know what " + line.split(" ")[0] + " is.", lineNum, -1, this.fileName).exit();
                        }
                    }
                } else {
                    if (line.startsWith("if")) {
                        ifs++;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                new ImpulseError("RunError", "Something unexpected happened while running the procedure.", -1, -1, this.fileName).exit();
            }
        }
        new ImpulseError("RunError", "No Return Value", -1, -1, this.fileName).exit();
        return 0;
    }

    private int attemptSetParam(double[] params, int paramNum, String line) {
        try {
            runParam(line, params[paramNum]);
            paramNum++;
        } catch (Exception e) {
            new ImpulseError("ParamError", "I was looking for " + (paramNum + 1) + (paramNum + 1 == 1 ? " parameter" : " parameters") + ", but only " + params.length + (params.length == 1 ? " parameter" : " parameters") + (params.length == 1 ? " was" : " were") + " given.", lineNum, colNum, this.fileName).exit();
        }
        return paramNum;
    }

    private double attemptReturn(String line) {
        try {
            return runReturn(line);
        } catch (Exception e) {
            new ImpulseError("ReturnError", "Return statement was not given a value, or something unexpected happened.", lineNum, colNum, this.fileName).exit();
        }
        return 0;
    }
}
