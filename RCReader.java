// Utility class to read .iplrc files in home directory
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class RCReader {
    private static final String[] DEFAULT_IPLRC_CONTENT = {
        "precision 10", "overflow 100", "epsilon 0.00000001", "mode normal"
    };

    private static final String[] VALID_CONFIG_KEYS = {
        "precision", "overflow", "epsilon", "mode"
    };

    public static String[] getDefaultIplrcContent() {
        return DEFAULT_IPLRC_CONTENT;
    }

    public static String[] read() {
        String[] lines = null;
        try {
            // Get user home directory
            String userHome = System.getProperty("user.home");
            // Write default .iplrc to user home directory
            String iplrcPath = userHome + "/.iplrc";
            FileReader fr = new FileReader(iplrcPath);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            ArrayList<String> linesList = new ArrayList<String>();
            while (line != null) {
                linesList.add(line);
                line = br.readLine();
            }
            lines = linesList.toArray(new String[linesList.size()]);
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("[WARN] Cannot read .iplrc file. Using default settings.");
            lines = DEFAULT_IPLRC_CONTENT;
        }
        return lines;
    }

    public static boolean isValidConfig(String line) {
        for (String key : VALID_CONFIG_KEYS) {
            if (line.startsWith(key)) {
                return true;
            }
        }
        return false;
    }

    public static int getPrecision(String[] lines) {
        for (String line : lines) {
            if (line.startsWith("precision")) {
                return Integer.parseInt(line.substring(10));
            }
        }
        return 10;
    }

    public static void write(String[] lines) {
        try {
            // Get user home directory
            String userHome = System.getProperty("user.home");
            // Write default .iplrc to user home directory
            String iplrcPath = userHome + "/.iplrc";
            FileWriter fw = new FileWriter(iplrcPath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("[WARN] Cannot write .iplrc file.");
        }
    }

    public static int getOverflow(String[] lines) {
        for (String line : lines) {
            if (line.startsWith("overflow")) {
                return Integer.parseInt(line.substring(9));
            }
        }
        return 100;
    }

    public static double getEpsilon(String[] lines) {
        for (String line : lines) {
            if (line.startsWith("epsilon")) {
                return Double.parseDouble(line.substring(8));
            }
        }
        return 0.00000001;
    }

    public static String getMode(String[] lines) {
        for (String line : lines) {
            if (line.startsWith("mode")) {
                return line.substring(5);
            }
        }
        return "normal";
    }

    public static void resetConfig() {
        try {
            // Get user home directory
            String userHome = System.getProperty("user.home");
            // Write default .iplrc to user home directory
            String iplrcPath = userHome + "/.iplrc";
            FileReader fr = new FileReader(iplrcPath);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            ArrayList<String> linesList = new ArrayList<String>();
            while (line != null) {
                linesList.add(line);
                line = br.readLine();
            }
            br.close();
            fr.close();
            // Write default .iplrc to user home directory
            FileWriter fw = new FileWriter(iplrcPath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String l : linesList) {
                if (isValidConfig(l)) {
                    bw.write(l);
                    bw.newLine();
                }
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("[WARN] Cannot reset .iplrc file.");
            System.out.println(e);
        }
    }

    public static void createIPLRC () {
        try {
            // Get user home directory
            String userHome = System.getProperty("user.home");
            // Write default .iplrc to user home directory
            String iplrcPath = userHome + "/.iplrc";
            FileWriter fw = new FileWriter(iplrcPath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String l : DEFAULT_IPLRC_CONTENT) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("[WARN] Cannot create .iplrc file.");
            System.out.println(e);
        }
    }

    public static boolean iplrcExists () {
        try {
            // Get user home directory
            String userHome = System.getProperty("user.home");
            // Write default .iplrc to user home directory
            String iplrcPath = userHome + "/.iplrc";
            FileReader fr = new FileReader(iplrcPath);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                if (isValidConfig(line)) {
                    return true;
                }
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}