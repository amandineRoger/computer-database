package com.excilys.cdb.control;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

        // Get application context
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        // EntityManagerFactory em = (EntityManagerFactory)
        // applicationContext.getBean("entityManagerFactory");
        Master master = (Master) applicationContext.getBean("master");

        TerminalView terminalView = new TerminalView();

        // Execution loop
        while (menuCommand != 0) {
            terminalView.displayMenu();
            menuCommand = scan.nextInt();
            if (!master.menuManager(menuCommand)) {
                terminalView.displayTypingError();
            }
        }
        ((ConfigurableApplicationContext) applicationContext).close();
        scan.close();
        LOGGER.debug("End of run loop, scanner closed");
    }
}
