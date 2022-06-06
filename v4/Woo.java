public class Woo{
	public static void main(String[] args){
		Runtime runtime = Runtime.getRuntime();
		String[] javacCommand = {"javac", "Procedure.java"};
		String[] iplrcCommand = {"echo", "precision 10", ">", "~/.iplrc"};
		String[] mkdirCommand = {"mkdir", "~/Impulse"};
		String[] cpBinCommand = {"cp", "*.class", "~/Impulse/"};
		String[] aliasCommand = {"alias", "impulse='java ~/Impulse/Procedure'"};
		String[] wToRCCommand = {"echo", "alias impulse='java ~/Impulse/Procedure'"};
		String[][] allCommands = {javacCommand, iplrcCommand, mkdirCommand, cpBinCommand, aliasCommand, wToRCCommand};
		for (String[] command : allCommands){
			try{
				Process p = runtime.exec(command);
				p.waitFor();
			} catch (Exception e){
				System.out.println(e);
			}
		}
	}
}
