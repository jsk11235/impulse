import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Procedure {
    String fileName;
    ArrayList<Double> vars;
    ArrayList<String> keys;
    ArrayList<String> src;
    int lineNum = 0;
    int colNum = 0;

    public Procedure(String name) throws FileNotFoundException {
        fileName = name;
        src = new ArrayList<>();
        vars = new ArrayList<>();
        keys = new ArrayList<>();
        File srcFile = new File("./"+name + ".ipl");
        Scanner reader = new Scanner(srcFile);
        while (reader.hasNextLine()) {
            String data = StringUtils.removeSpaces(reader.nextLine());
            if (data.length() > 0) src.add(data);
        }
        reader.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        double[] params = null;
        try {
            params = new double[args.length - 1];
        } catch (Exception e) {
            new ImpulseError("NoFile", "You must provide a file to run.", -1, -1, null).exit();
        }
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
        try {
            proc = new Procedure(procName);
        } catch (FileNotFoundException e) {
            new ImpulseError("FileNotFound", "The file " + procName + ".ipl was not found.", -1, -1, null).exit();
        }
        proc.run(params);
        System.out.println(proc.run(params));
    }

    public String toString() {
        return "Procedure{" + "vars=" + vars + ", keys=" + keys + ", src=" + src + '}';
    }

    public double doMath(String math) {
        String newMath = StringUtils.removeSpaces(math);
        for (int i = 0; i < vars.size(); i++)
            newMath = newMath.replaceAll(keys.get(i), Double.toString(vars.get(i)));
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
        String name = line.substring(5);
        System.out.println(name);
    }

    public boolean runIf(String line) {
        String ref = line.substring(2);
        for (int i = 0; i < vars.size(); i++)
            ref = ref.replaceAll(keys.get(i), Double.toString(vars.get(i)));
        return BooleanParser.parseBool(ref);
    }

    public void runRes(String line) throws FileNotFoundException {
        for (int i = 0; i < vars.size(); i++)
            line = line.replaceAll(keys.get(i), Double.toString(vars.get(i)));
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
        Procedure newProc = new Procedure(procName);
        keys.add(varName);
        vars.add(newProc.run(newParams));
    }

    public double run(double[] params) throws FileNotFoundException {
        int paramNum = 0;
        boolean runnable = true;
        int ifs = 0;
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
                    if (line.startsWith("return")) {
                        colNum += 6;
                        try {
                            return runReturn(line);
                        } catch (Exception e) {
                            new ImpulseError("ReturnError", "Return statement was not given a value, or something unexpected happened.", lineNum, colNum, this.fileName).exit();
                        }
                    } else if (line.startsWith("param")) {
                        colNum += 5;
                        try {
                            runParam(line, params[paramNum]);
                            paramNum++;
                        } catch (Exception e) {
                            new ImpulseError("ParamError", "I was looking for " + (paramNum + 1) + (paramNum + 1 == 1 ? " parameter" : " parameters") + ", but only " + params.length + (params.length == 1 ? " parameter" : " parameters") + (params.length == 1 ? " was" : " were") + " given.", lineNum, colNum, this.fileName).exit();
                        }
                    } else if (line.startsWith("print")){
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
                    } else {
                      new ImpulseError("CompileError", "Syntax unintelligble", lineNum, -1, this.fileName).exit();
                    }
                } else {
                    if (line.startsWith("if")) {
                        ifs++;
                    }
                }
            } catch (Exception e) {
                new ImpulseError("RunError", "Something unexpected happened while running the procedure.", -1, -1, this.fileName).exit();
            }
        }
        new ImpulseError("RunError", "No Return Value", -1, -1, this.fileName).exit();
        return 0;
    }
}
