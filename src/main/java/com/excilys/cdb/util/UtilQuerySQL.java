package com.excilys.cdb.util;

public interface UtilQuerySQL {
    String COMPUTER_TABLE = "computer";
    String COMPANY_TABLE = "company";

    int COL_ID = 1;
    int COL_NAME = 2;
    int COL_INTRODUCED = 3;
    int COL_DISCONTINUED = 4;
    int COL_COMPANY_ID = 5;
    int COL_COMPANY_NAME = 6;

    // Queries
    String ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id, o.name FROM "
            + COMPUTER_TABLE + " c LEFT JOIN " + COMPANY_TABLE
            + " o ON c.company_id = o.id ";
    String ALL_COMPUTERS_P = ALL_COMPUTERS + " LIMIT ?, ?";
    String COMPUTER_BY_ID = ALL_COMPUTERS + " WHERE c.id = ?";
    String CREATE_COMPUTER = "INSERT INTO " + COMPUTER_TABLE
            + " (name, introduced, discontinued, company_id ) VALUES ( ?, ?, ?, ?)";
    String UPDATE_COMPUTER = "UPDATE " + COMPUTER_TABLE
            + " SET name = ?, introduced = ?,"
            + "discontinued = ?, company_id = ? WHERE id = ?";
    String DELETE_COMPUTER = "DELETE FROM " + COMPUTER_TABLE + " WHERE id = ?";
    String ALL_COMPANIES = "SELECT id, name FROM " + COMPANY_TABLE;
    String ALL_COMPANIES_P = ALL_COMPANIES + " LIMIT ?, ?";
    String COMPANY_BY_ID = ALL_COMPANIES + " WHERE id = ?";

    String COUNT_COMPUTERS = "SELECT COUNT(*) FROM " + COMPUTER_TABLE;
    String COUNT_COMPANIES = "SELECT COUNT(*) FROM " + COMPANY_TABLE;
}