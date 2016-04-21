package control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;

import dao.CompanyDAO;
import dao.ComputerDAO;
import entities.Company;
import entities.Computer;
import util.Utils;
import view.TerminalView;

public class Master {
	private static ComputerDAO computerDao = new ComputerDAO();
	private static CompanyDAO companyDao = new CompanyDAO();
	
	public static boolean menuManager(int command){
		boolean commandOk = true;
		long id;
		ArrayList<Computer> computer;
		
		switch (command) {
		case 0:
			//quit program
			break;
		case 1:
			// get computers list
			TerminalView.displayResults(computerDao.getComputerList()); 
			break;
		case 2:
			//get companies list
			TerminalView.displayResults(companyDao.getCompanyList());
			break;
		case 3:
			//get computer details
			System.out.println("Computer details _ type ID of wanted computer : ");
			id = CLI.scan.nextLong();
			computer = computerDao.getComputerDetail(id);
			if (computer.isEmpty()) {
				commandOk = false;
			} else {
				TerminalView.displayResults(computer);
			}
			break;
		case 4:
			//create computer
			System.out.println("You just create the following computer : ");
			TerminalView.displayResults(computerDao.createComputer());
			break;
		case 5:
			//update computer
			System.out.println("You just update the following computer : ");
			TerminalView.displayResults(computerDao.updateComputer());
			break;
		case 6:
			//delete computer
			System.out.println("Computer details _ type ID of wanted computer : ");
			id = CLI.scan.nextLong();
			computer = computerDao.deleteComputer(id);
			if (computer.isEmpty()) {
				commandOk = false;
			} else {
				System.out.println("You just delete the following computer : ");
				TerminalView.displayResults(computer);
			}
			break;
		default:
			commandOk = false;
			break;
		}
		
		return commandOk;
	}
	
	public static Computer getComputerFromUser(){
		Computer computer = new Computer();
		
		System.out.println("*** Computer creation : Please insert following informations ...");
		System.out.print(" *  computer name : ");
		/* Computer name */
		String name = CLI.scan.next();
		//check if name format is valid
		while (name.isEmpty() || name.trim().isEmpty()){
			System.out.println("Invalid name, please retry ...");
			name = CLI.scan.next();
		}
		computer.setName(name);
		
		/* Introduced date */
		System.out.println(" *  introduced on : (type 0 if you don't want to insert date) ");
		computer.setIntroduced(getDateFromUser());
		
		/* Discontinued date*/
		System.out.println(" *  discontinued on : (type 0 if you don't want to insert date) ");
		computer.setDiscontinued(getDateFromUser());
		
		/* Company */
		System.out.println(" *  Company ID : (type 0 if you don't want to insert a company");
		getIntFromUser(COMPANY_ID);
		computer.setCompany(tmpCompany);
		
		return computer;
	}
	
	private static LocalDate getDateFromUser(){
		LocalDate date = null;
		System.out.print(" *   : year ");
		int year = getIntFromUser(YEAR);
		if ( year != 0){
			int month, day;
			System.out.print(" *   : month ");
			month = getIntFromUser(MONTH);
			System.out.print(" *   : day ");
			day = getIntFromUser(DAY);
			
			date = LocalDate.of(year, month, day);			
		}
		return date;
	}
	
	private static final int YEAR = 1;
	private static final int MONTH = 2;
	private static final int DAY = 3;
	private static final int COMPANY_ID = 4;
	private static final int COMPUTER_ID = 5;
	
	private static Company tmpCompany;
	private static Computer tmpComputer;
	
	private static int getIntFromUser(int type){
		int value = 0;
		boolean formatOK = false;
		
		while (!formatOK) {
			try {
				value = CLI.scan.nextInt();
				formatOK = true;
				switch (type) {
					case YEAR:
						if ( (value < 1952 && value != 0) || value > Utils.CURRENT_YEAR) {
							formatOK = false;
							System.out.println("Invalid value: year must be between 1952 and current year, please retry ...");
						} 
						break;
					case MONTH:
						if (value<1 || value > 12) {
							formatOK = false;
							System.out.println("Invalid value: month must be between 1 and 12, please retry ...");
						}
						break;
					case DAY:
						if (value < 1 || value > 31){
							formatOK = false;
							System.out.println("Invalid value: day must be between 1 and 31, please retry ...");
						}
						break;
					case COMPANY_ID:
						if (value != 0){
							tmpCompany = companyDao.getCompanyById(value);
							if (tmpCompany == null) {
								formatOK = false;
								System.out.println("Invalid ID: this company doesn't exist, please retry ...");
							} 
						}
						break;
					case COMPUTER_ID:
						tmpComputer = computerDao.getComputerDetail(value).get(0);
						if (tmpComputer == null){
							formatOK = false;
							System.out.println("Invalid ID: this computer doesn't exist, please retry ...");
						}
						break;
				}
				
			} catch (InputMismatchException e) {
				formatOK = false;
				System.out.println( "Invalid format, please retry ..." );
				e.printStackTrace();
			}
		}

		return value;
	}
	
	public static Computer getComputerUpdateFromUser(){
		System.out.println("*** Computer update : Please insert following informations ...");
		System.out.print(" *  ID :");
		getIntFromUser(COMPUTER_ID);
		System.out.print(" * Current name = ");
		System.out.println(tmpComputer.getName());
		System.out.println(" * Change name ? (Y/N)");
		String stringTyping = CLI.scan.next();
		/* Change name */
		if (stringTyping.toUpperCase().equals("Y")){

			System.out.print("new name = ");
			stringTyping = CLI.scan.next();
			//check if name format is valid
			while (stringTyping.isEmpty() || stringTyping.trim().isEmpty()){
				System.out.println("Invalid name, please retry ...");
				stringTyping = CLI.scan.next();
			}
			
			tmpComputer.setName(stringTyping);
		}
		
		/* Change introduced date */
		System.out.print(" * Current introduced date = ");
		System.out.println(tmpComputer.getIntroduced());
		System.out.println(" * Change introduced date ? (Y/N)");
		stringTyping = CLI.scan.next();
		if (stringTyping.toUpperCase().equals("Y")){
			LocalDate date = getDateFromUser();
			tmpComputer.setIntroduced(date);
		}

		/* Change discontinued date */
		System.out.print(" * Current discontinued date = ");
		System.out.println(tmpComputer.getIntroduced());
		System.out.println(" * Change discontinued date ? (Y/N)");
		stringTyping = CLI.scan.next();
		if (stringTyping.toUpperCase().equals("Y")){
			LocalDate date = getDateFromUser();
			tmpComputer.setDiscontinued(date);
		}
		
		/* Change company */
		System.out.print("Current company = ");
		System.out.println(tmpComputer.getCompany());
		
		System.out.println(" * Change company ? (Y/N)");
		stringTyping = CLI.scan.next();
		if (stringTyping.toUpperCase().equals("Y")){
			System.out.print(" * insert company ID : ");
			getIntFromUser(COMPANY_ID);
			tmpComputer.setCompany(tmpCompany);
		}
		
		return tmpComputer;
	}
	
	

}
