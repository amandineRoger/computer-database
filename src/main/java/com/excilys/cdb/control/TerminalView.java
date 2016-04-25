package main.java.com.excilys.cdb.control;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminalView {
	private static final Logger logger = LoggerFactory.getLogger(TerminalView.class);
	
	/**
	 * Display menu on CLI
	 */
	public static void displayMenu() {
		logger.debug("f_displayMenu");
		System.out.println(" ********  Menu  ******* ");
		System.out.println(" 0 - Quit program");
		System.out.println(" 1 - List of computers");
		System.out.println(" 2 - List of companies");
		System.out.println(" 3 - Show computer details");
		System.out.println(" 4 - Create new computer");
		System.out.println(" 5 - Update a computer");
		System.out.println(" 6 - Delete a computer");
		System.out.println(" *********************** ");
		System.out.println(" Type command : ");
	}

	/**
	 * Display results from ArrayList (toString() of item has to be defined)
	 * 
	 * @param list
	 *            an ArrayList of Objects
	 */
	public static void displayResults(ArrayList<?> list) {
		logger.debug("f_displayResults");
		int size = list.size();
		for (int i = 0; i < size; i++) {
			System.out.println(list.get(i).toString());
		}
	}

	public static void displayObject(Object o) {
		logger.debug("f_displayObject");
		System.out.println(o.toString());
	}

	/**
	 * Display error message when user entry was invalid
	 */
	public static void displayTypingError() {
		logger.debug("f_displayTypingError");
		System.out.println("Invalid command ! please retry with valid one !");
		;
	}

}
