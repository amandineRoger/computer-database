package control;

import java.util.Scanner;

import view.TerminalView;

public class CLI {
	public static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		scan.useDelimiter("\\n");
		int menuCommand = -1;
		
		while (menuCommand != 0){
			TerminalView.displayMenu();
			menuCommand = scan.nextInt();
			
			if (!Master.menuManager(menuCommand)){
				TerminalView.displayTypingError();
			}
		}
		scan.close();
	}
	
	
	

}
