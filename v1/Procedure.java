import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Procedure {
    ArrayList<Double> vars;
    ArrayList<String> keys;
    ArrayList<String> src;

    public Procedure(String name) throws FileNotFoundException {
        src = new ArrayList<>();
        vars = new ArrayList<>();
        keys = new ArrayList<>();
        File srcFile = new File("./examples/" + name + ".ipl");
        Scanner reader = new Scanner(srcFile);
        while (reader.hasNextLine()) {
            String data = StringUtils.removeSpaces(reader.nextLine());
            if (data.length() > 0)
                src.add(data);
        }
        reader.close();
    }

    public String toString() {
        return "Procedure{" +
                "vars=" + vars +
                ", keys=" + keys +
                ", src=" + src +
                '}';
    }

    public double doMath(String math) {
        String newMath = StringUtils.removeSpaces(math);
        for (int i = 0; i < vars.size(); i++)
            newMath = newMath.replaceAll(keys.get(i), Double.toString(vars.get(i)));
        try{
            return Double.parseDouble(math);
        }catch (Exception e){}
        return MathUtils.eval(newMath);
    }

    public double runReturn(String line) {
        String ref = line.substring(6);
        for (int i = 0; i < keys.size(); i++) {
            if (ref.equals(keys.get(i)))
                return vars.get(i);
        }
        return doMath(ref);
    }

    public void runParam(String line, double value) {
        String name = line.substring(5);
        keys.add(name);
        vars.add(value);
    }

    public void runVar(String line){
        int equalsIdx = line.indexOf("=");
        String name = line.substring(4,equalsIdx);
        String ref = line.substring(equalsIdx+1);
        if (keys.contains(name))
            System.out.println("Variable already declared");
        else{
            keys.add(name);
            vars.add(doMath(ref));
        }
    }

    public double run(double[] params) {
        int paramNum = 0;
        for (String line : src) {
            if (line.startsWith("return")) {
                return runReturn(line);
            } else if (line.startsWith("param")) {
                runParam(line, params[paramNum]);
                paramNum++;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        double[] params = new double[args.length - 1];
        int argNum = 0;
        String procName = "";
        for (String arg : args) {
            if (argNum == 0)
                procName = arg;
            else
                params[argNum - 1] = Double.parseDouble(arg);
            argNum++;
        }
        Procedure proc = new Procedure(procName);
        System.out.println(proc.run(params));
    }
}
