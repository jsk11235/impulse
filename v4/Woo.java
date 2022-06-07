public class Woo{
	public static void main(String[] args){
		Runtime runtime = Runtime.getRuntime();
		String home = System.getProperty("user.home");
		System.out.println(home);
		String current  = System.getProperty("user.dir");
		System.out.println(current);

		String[] javacCommand = {"javac", "Procedure.java"};
		String[] iplrcCommand = {"echo", "precision 8", ">", home+"/.iplrc"};
		String[] wToRCCommand = {"echo", "alias impulse='java -cp "+current+" Procedure'",">", home+"/.bashrc"};
		String[][] allCommands = {javacCommand, iplrcCommand, wToRCCommand};
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
