package control;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLI {
	public static Scanner scan = new Scanner(System.in);
	private static final Logger logger = LoggerFactory.getLogger(CLI.class);

	public static void main(String[] args) {
		logger.debug("Enter in main");
		scan.useDelimiter("\\n");
		int menuCommand = -1;

		// Execution loop
		while (menuCommand != 0) {
			TerminalView.displayMenu();
			menuCommand = scan.nextInt();

			if (!Master.menuManager(menuCommand)) {
				TerminalView.displayTypingError();
			}
		}
		scan.close();
		logger.warn("TEST WARN !!");
		logger.error("TEST ERROR !");
		logger.debug("End of run loop, scanner closed");
	}
}
