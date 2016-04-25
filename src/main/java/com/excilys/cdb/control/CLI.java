package main.java.com.excilys.cdb.control;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLI {
    public static Scanner scan = new Scanner(System.in);
    private static final Logger LOGGER = LoggerFactory.getLogger(CLI.class);

    /**
     * Main function for application.
     *
     * @param args
     *            no argument is required to run
     */
    public static void main(String[] args) {
        LOGGER.debug("Enter in main");
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
        LOGGER.warn("TEST WARN !!");
        LOGGER.error("TEST ERROR !");
        LOGGER.debug("End of run loop, scanner closed");
    }
}
