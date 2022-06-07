// BufferedReader
import java.io.*;

public class Woo{
	public static void main(String[] args){
		Runtime runtime = Runtime.getRuntime();
		String[] javacCommand = {"javac", "Procedure.java"};
		// Get user home directory
		String userHome = System.getProperty("user.home");
		// Write default .iplrc to user home directory
		String iplrcPath = userHome + "/.iplrc";
		File iplrcFile = new File(iplrcPath);
		try{
			iplrcFile.createNewFile();
		}catch(IOException e){
			System.out.println("Error: Cannot create .iplrc file.");
			System.exit(1);
		}
		// Write default .iplrc file
		// Default:
		// precision 10
		String iplrcContent = "precision 10\n";
		try{
			FileWriter iplrcWriter = new FileWriter(iplrcFile);
			iplrcWriter.write(iplrcContent);
			iplrcWriter.close();
		}catch(IOException e){
			System.out.println("Error: Cannot write to .iplrc file.");
			System.exit(1);
		}
		String currentPath = System.getProperty("user.dir");
		// .bashrc can be found in the user's home directory
		String bashrcPath = userHome + "/.bashrc";
		System.out.println(currentPath);
		String[][] allCommands = {javacCommand};
		for (String[] command : allCommands){
			try{
				Process p = runtime.exec(command);
				p.waitFor();
				// Print the output of the command
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = reader.readLine();
				while (line != null){
					System.out.println(line);
					line = reader.readLine();
				}
			} catch (Exception e){
				System.out.println(e);
			}
		}
		System.out.println("You should restart your terminal to use the 'impulse' command.");
	}
}
