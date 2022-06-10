// BufferedReader

import java.io.*;

/* MEGA NOTE:
 * All the sleeping this program does is for cosmetic purposes only. Nothing will change if you remove the sleep.
 */

public class Woo {
    private static void printImpulse() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./assets/ImpulseTerminalLogo.txt"));
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("[WARN] Cannot read ImpulseTerminalLogo.txt");
        }
    }

    private static void installImpulse() {
        System.out.println("[INFO] Installing Impulse...");
        System.out.print(Colors.progress(2, 20, "Creating an .iplrc file..."));
        try { Thread.sleep(400); } catch (InterruptedException e) {}
        // Write a default .iplrc file to user home directory, this can be done by calling RCReader.resetConfig()
        if (RCReader.iplrcExists()) {
            // Prompt user if they want to overwrite the existing .iplrc file
            System.out.println(Colors.yellow("\n[WARN] .iplrc file already exists. Overwrite with default configuration? (y/n)" + Colors.reset()));
            // Read user input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = "";
            try {
                input = br.readLine();
            } catch (IOException e) {
                System.out.println("[ERROR] Cannot read user input.");
            }
            // If user wants to overwrite the existing .iplrc file, reset the configuration
            if (input.trim().equals("y")) {
                RCReader.write(RCReader.getDefaultIplrcContent());
                System.out.println(Colors.bold(Colors.green("[INFO] .iplrc file overwritten.")));
            } else {
                System.out.println(Colors.bold(Colors.green("[INFO] Skipping iplrc overwrite.")));
            }
        } else {
            RCReader.createIPLRC();
        }

        // Create an alias for the Impulse command so the user can type "impulse" instead of "java Procedure"
        System.out.print(Colors.bol());
        System.out.print(Colors.progress(4, 20, "Reading .bashrc file..."));
        try { Thread.sleep(250); } catch (InterruptedException e) {}

        // Create a new alias in the user's .bashrc file
        boolean aliasExists = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/.bashrc"));
            String line = br.readLine();
            while (line != null) {
                if (line.contains("alias impulse")) {
                    aliasExists = true;
                    break;
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot read .bashrc file.");
        }
        try { Thread.sleep(250); } catch (InterruptedException e) {}

        // Write the alias to the .bashrc file
        System.out.print(Colors.bol());
        System.out.print(Colors.progress(6, 20, "Writing alias to .bashrc file..."));
        if (!aliasExists) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/.bashrc", true));
                // Find the directory where this program is located
                String path = new File(".").getAbsolutePath();
                path = path.substring(0, path.length() - 1);
                bw.newLine();
                bw.write("alias impulse=\"java -classpath " + path + " Procedure\"");
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                System.out.println("[ERROR] Cannot write to .bashrc file.");
            }
        } else {

        }
        try { Thread.sleep(250); } catch (InterruptedException e) {}

        // Reload the .bashrc file
        System.out.print(Colors.bol());
        System.out.print(Colors.progress(8, 20, "Reloading .bashrc file..."));
        try {
            Runtime.getRuntime().exec("source ~/.bashrc");
        } catch (IOException e) {

        }
        try { Thread.sleep(250); } catch (InterruptedException e) {}

        // Compile files in order
        String[] order = {
                "./ArrayUtils.java",
                "./Colors.java",
                "./RCReader.java",
                "./ImpulseError.java",
                "./StringUtils.java",
                "./MathParser.java",
                "./MathUtils.java",
                "./BooleanParser.java",
                "./REPL.java",
                "./Procedure.java",
        };

        // Compile all files in the order array
        for (int i = 0; i < order.length; i++) {
            System.out.print(Colors.bol());
            System.out.print(Colors.progress(10 + i, 20, "Compiling " + order[i] + "..."));
            try {
                Runtime.getRuntime().exec("javac " + order[i]);
            } catch (IOException e) {
                System.out.println("[ERROR] Cannot compile " + order[i]);
            }
            try { Thread.sleep(100); } catch (InterruptedException e) {
                System.out.println(Colors.bold(Colors.red("[ERROR] Cannot compile " + order[i])));
                System.out.println(Colors.bold(Colors.red("Aborting Impulse installation.")));
                System.exit(1);
            }
        }

        // Check installation
        System.out.print(Colors.bol());
        System.out.print(Colors.progress(18, 20, "Checking installation..."));
        if (RCReader.iplrcExists()) {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        } else {
            System.out.println(Colors.bold(Colors.red("[ERROR] Impulse installation failed.")));
        }

        // Print full progress bar and a message
        System.out.print(Colors.bol());
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.print(Colors.progress(20, 20, "Impulse installation complete."));
        System.out.println(Colors.bold(Colors.green("\n[INFO] Impulse is now installed. To start Impulse, restart your terminal and run \"impulse\".")));

        // Exit the program
        System.exit(0);
    }

    public static void main(String[] args) {
        // Read ImpulseTerminalLogo.txt and print it to the console
        printImpulse();

        // Print a welcome message
        System.out.println("This is Impulse, a simple programming language.");
        System.out.println("Created by DrBracket.");
        System.out.println(Colors.red("WARNING: Running this program could modify your home directory."));

        // Print a message stating that running this script will help the user install Impulse on their computer
        System.out.println(Colors.blue("Do you want to install Impulse on your computer? (y/n)" + Colors.reset()));

        // Read the user's input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            input = br.readLine();
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot read user input.");
        }

        // If the user said "y", install Impulse
        if (input.trim().equals("y")) {
            installImpulse();
        } else {
            System.out.println(Colors.bold(Colors.red("User aborted installation.")));
        }
    }
}
