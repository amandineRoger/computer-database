package view;

import java.util.ArrayList;

public class TerminalView {

	/**
	 * Display menu on CLI
	 */
	public static void displayMenu() {
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
		int size = list.size();
		for (int i = 0; i < size; i++) {
			System.out.println(list.get(i).toString());
		}
	}

	/**
	 * Display error message when user entry was invalid
	 */
	public static void displayTypingError() {
		System.out.println("Invalid command ! please retry with valid one !");
		;
	}

}
