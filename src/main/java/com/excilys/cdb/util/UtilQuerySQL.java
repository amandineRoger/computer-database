package com.excilys.cdb.util;

public interface UtilQuerySQL {
    public final String COMPUTER_TABLE = "computer";
    public final String COMPANY_TABLE = "company";

    public final int COL_ID = 1;
    public final int COL_NAME = 2;
    public final int COL_INTRODUCED = 3;
    public final int COL_DISCONTINUED = 4;
    public final int COL_COMPANY_ID = 5;
    public final int COL_COMPANY_NAME = 6;

    // Queries
    public final String ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id, o.name FROM "
            + COMPUTER_TABLE + " c LEFT JOIN " + COMPANY_TABLE
            + " o ON c.company_id = o.id ";
    public final String ALL_COMPUTERS_P = ALL_COMPUTERS + " LIMIT ?, ?";
    public final String COMPUTER_BY_ID = ALL_COMPUTERS + " WHERE c.id = ?";
    public final String CREATE_COMPUTER = "INSERT INTO " + COMPUTER_TABLE
            + " (name, introduced, discontinued, company_id ) VALUES ( ?, ?, ?, ?)";
    public final String UPDATE_COMPUTER = "UPDATE " + COMPUTER_TABLE
            + " SET name = ?, introduced = ?,"
            + "discontinued = ?, company_id = ? WHERE id = ?";
    public final String DELETE_COMPUTER = "DELETE FROM " + COMPUTER_TABLE
            + " WHERE id = ?";
    public final String ALL_COMPANIES = "SELECT id, name FROM " + COMPANY_TABLE;
    public final String ALL_COMPANIES_P = ALL_COMPANIES + " LIMIT ?, ?";
    public final String COMPANY_BY_ID = ALL_COMPANIES + " WHERE id = ?";

    public final String COUNT_COMPUTERS = "SELECT COUNT(*) FROM "
            + COMPUTER_TABLE;
    public final String COUNT_COMPANIES = "SELECT COUNT(*) FROM "
            + COMPANY_TABLE;
}