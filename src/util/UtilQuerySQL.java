package util;

public interface UtilQuerySQL {
	public static final String COMPUTER_TABLE = "computer";
	public static final String COMPANY_TABLE = "company";

	// Rename constants?
	public static final int COL_ID = 1;
	public static final int COL_NAME = 2;
	public static final int COL_INTRODUCED = 3;
	public static final int COL_DISCONTINUED = 4;
	public static final int COL_COMPANY_ID = 5;
	public static final int COL_COMPANY_NAME = 6;

	// Queries
	public static final String ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id, o.name FROM "
			+ COMPUTER_TABLE + " c LEFT JOIN " + COMPANY_TABLE + " o ON c.company_id = o.id ";
	public static final String COMPUTER_BY_ID = ALL_COMPUTERS + " WHERE c.id = ?";
	public static final String CREATE_COMPUTER = "INSERT INTO " + COMPUTER_TABLE
			+ " (name, introduced, discontinued, company_id ) VALUES ( ?, ?, ?, ?)";
	public static final String UPDATE_COMPUTER = "UPDATE " + COMPUTER_TABLE + " SET name = ?, introduced = ?,"
			+ "discontinued = ?, company_id = ? WHERE id = ?";
	public static final String DELETE_COMPUTER = "DELETE FROM " + COMPUTER_TABLE + " WHERE id = ?";
	
	public static final String ALL_COMPANIES = "SELECT id, name FROM "+ COMPANY_TABLE ;
	public static final String COMPANY_BY_ID = ALL_COMPANIES + " WHERE id = ?";
}