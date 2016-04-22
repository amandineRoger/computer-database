package control;

import java.time.LocalDate;
import java.util.InputMismatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.CompanyDAO;
import dao.ComputerDAO;
import entities.Company;
import entities.Computer;

public class Master {
	private static ComputerDAO computerDao = ComputerDAO.getInstance();
	private static CompanyDAO companyDao = CompanyDAO.getInstance();
	private static final Logger logger = LoggerFactory.getLogger(Master.class);

	/**
	 * Manage the entered command from menu
	 * 
	 * @param command
	 *            user entry from keyboard
	 * @return boolean true if command is valid, false otherwise
	 */
	public static boolean menuManager(int command) {
		logger.debug("f_menuManager( {} )", command);
		boolean commandOk = true;
		long id;
		Computer computerEntity;

		switch (command) {
		case 0:
			// quit program
			break;
		case 1:
			// get computers list
			TerminalView.displayResults(computerDao.getComputerList());
			break;
		case 2:
			// get companies list
			TerminalView.displayResults(companyDao.getCompanyList());
			break;
		case 3:
			// get computer details
			System.out.println("Computer details _ type ID of wanted computer : ");
			id = CLI.scan.nextLong();
			computerEntity = computerDao.getComputerDetail(id);
			if (computerEntity == null) {
				commandOk = false;
			} else {
				TerminalView.displayObject(computerEntity);
				;
			}
			break;
		case 4:
			// create computer
			System.out.println("You just create the following computer : ");
			TerminalView.displayObject(computerDao.createComputer());
			break;
		case 5:
			// update computer
			System.out.println("You just update the following computer : ");
			TerminalView.displayObject(computerDao.updateComputer());
			break;
		case 6:
			// delete computer
			System.out.println("Computer details _ type ID of wanted computer : ");
			id = CLI.scan.nextLong();
			computerEntity = computerDao.deleteComputer(id);
			if (computerEntity == null) {
				commandOk = false;
			} else {
				System.out.println("You just delete the following computer : ");
				TerminalView.displayObject(computerEntity);
			}
			break;
		default:
			commandOk = false;
			break;
		}
		return commandOk;
	}

	/**
	 * Manage the computer creation by user, step by step
	 * 
	 * @return computer entity which was just created
	 */
	public static Computer getComputerFromUser() {
		logger.debug("f_getComputerFromUser");

		System.out.println("*** Computer creation : Please insert following informations ...");
		System.out.print(" *  computer name : ");
		/* Computer name */
		String name = CLI.scan.next();
		// check if name format is valid
		while (name.isEmpty() || name.trim().isEmpty()) {
			System.out.println("Invalid name, please retry ...");
			name = CLI.scan.next();
		}
		Computer.Builder builder = new Computer.Builder(name);

		/* Introduced date */
		System.out.println(" *  introduced on : (type 0 if you don't want to insert date) ");
		builder.introduced(getDateFromUser());

		/* Discontinued date */
		System.out.println(" *  discontinued on : (type 0 if you don't want to insert date) ");
		builder.discontinued(getDateFromUser());

		/* Company */
		System.out.println(" *  Company ID : (type 0 if you don't want to insert a company");
		getIntFromUser(COMPANY_ID);
		builder.company(tmpCompany);

		return builder.build();
	}

	/**
	 * Manage the creation of a localDate by User, step by step
	 * 
	 * @return LocalDate which was just created
	 */
	private static LocalDate getDateFromUser() {
		logger.debug("f_getDateFromUser");
		LocalDate date = null;
		System.out.print(" *   : year ");
		int year = getIntFromUser(YEAR);
		if (year != 0) {
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

	/**
	 * Manage the integer entry by user, loop while format is not valid
	 * 
	 * @param type
	 *            which type of treatment is waited for the integer
	 * @return wanted value
	 */
	private static int getIntFromUser(int type) {
		logger.debug("f_getIntFromUser");
		int value = 0;
		boolean formatOK = false;

		while (!formatOK) {
			try {
				value = CLI.scan.nextInt();
				formatOK = true;
				switch (type) {
				case YEAR:
					if ((value < 1970 && value != 0) || value > 2037) {
						formatOK = false;
						System.out.println("Invalid value: year must be between 1970 and 2037, please retry ...");
					}
					break;
				case MONTH:
					if (value < 1 || value > 12) {
						formatOK = false;
						System.out.println("Invalid value: month must be between 1 and 12, please retry ...");
					}
					break;
				case DAY:
					if (value < 1 || value > 31) {
						formatOK = false;
						System.out.println("Invalid value: day must be between 1 and 31, please retry ...");
					}
					break;
				case COMPANY_ID:
					if (value != 0) {
						tmpCompany = companyDao.getCompanyById(value);
						if (tmpCompany == null) {
							formatOK = false;
							System.out.println("Invalid ID: this company doesn't exist, please retry ...");
						}
					}
					break;
				case COMPUTER_ID:
					tmpComputer = computerDao.getComputerDetail(value);
					if (tmpComputer == null) {
						formatOK = false;
						System.out.println("Invalid ID: this computer doesn't exist, please retry ...");
					}
					break;
				}
			} catch (InputMismatchException e) {
				formatOK = false;
				System.out.println("Invalid format, please retry ...");
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * Manage the user entry for updating a computer
	 * 
	 * @return the computer which was updated (with modifications)
	 */
	public static Computer getComputerUpdateFromUser() {
		logger.debug("f_getComputerUpdateFromUser");
		System.out.println("*** Computer update : Please insert following informations ...");
		System.out.print(" *  ID :");
		getIntFromUser(COMPUTER_ID);
		System.out.print(" * Current name = ");
		System.out.println(tmpComputer.getName());
		System.out.println(" * Change name ? (Y/N)");
		String stringTyping = CLI.scan.next();
		/* Change name */
		if (stringTyping.toUpperCase().equals("Y")) {
			System.out.print("new name = ");
			stringTyping = CLI.scan.next();
			// check if name format is valid
			while (stringTyping.isEmpty() || stringTyping.trim().isEmpty()) {
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
		if (stringTyping.toUpperCase().equals("Y")) {
			LocalDate date = getDateFromUser();
			tmpComputer.setIntroduced(date);
		}

		/* Change discontinued date */
		System.out.print(" * Current discontinued date = ");
		System.out.println(tmpComputer.getIntroduced());
		System.out.println(" * Change discontinued date ? (Y/N)");
		stringTyping = CLI.scan.next();
		if (stringTyping.toUpperCase().equals("Y")) {
			LocalDate date = getDateFromUser();
			tmpComputer.setDiscontinued(date);
		}

		/* Change company */
		System.out.print("Current company = ");
		System.out.println(tmpComputer.getCompany());
		System.out.println(" * Change company ? (Y/N)");
		stringTyping = CLI.scan.next();
		if (stringTyping.toUpperCase().equals("Y")) {
			System.out.print(" * insert company ID : ");
			getIntFromUser(COMPANY_ID);
			tmpComputer.setCompany(tmpCompany);
		}

		return tmpComputer;
	}

}
