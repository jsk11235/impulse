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
            String data = reader.nextLine();
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

    public double doMath(String math){
        return Double.parseDouble(math);
    }

    public double runReturn(String line){
        String ref = line.substring(7);
        for (int i = 0;i<keys.size();i++){
            if (ref.equals(keys.get(i)))
                return vars.get(i);
        }
        return doMath(ref);
    }

    public void runParam(String line, double value) {
        String name = line.substring(6);
        keys.add(name);
        vars.add(value);
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
        Procedure proc = new Procedure("add");
        System.out.println(proc);
        double[] blah = {1,2};
        System.out.println(proc.run(blah));
    }
}
