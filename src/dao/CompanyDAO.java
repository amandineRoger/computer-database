package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bdd.SingleConnect;
import entities.Company;

public class CompanyDAO {

	private static final String TABLE = "company";
	private static final int COL_ID = 1;
	private static final int COL_NAME = 2;

	private SingleConnect singleConnect;
	private Connection connect;

	public CompanyDAO() {
		this.singleConnect = SingleConnect.getInstance();
	}

	/**
	 * Get all companies from database
	 * 
	 * @return ArrayList<Company> all companies
	 */
	public ArrayList<Company> getCompanyList() {
		ArrayList<Company> list = new ArrayList<>();

		// Query
		String query = "SELECT * from " + TABLE;
		ResultSet results = null;
		connect = singleConnect.getConnection();

		try {
			PreparedStatement ps = connect.prepareStatement(query);
			results = ps.executeQuery();
			Company tmp;
			while (results.next()) {
				tmp = new Company();
				tmp.setId(results.getLong(COL_ID));
				tmp.setName(results.getString(COL_NAME));
				list.add(tmp);
			}
			connect.close();
		} catch (SQLException e) {
			System.out.println("Company DAO says : getCompanyList _ " + e.getMessage());
		}

		return list;
	}

	/**
	 * Find a company by its id
	 * 
	 * @param id
	 *            id of the wanted company
	 * @return wanted company
	 */
	public Company getCompanyById(long id) {
		Company company = null;
		if (id != 0) {
			StringBuffer query = new StringBuffer("SELECT name FROM ");
			query.append(TABLE);
			query.append(" WHERE id = ");
			query.append(id);

			connect = singleConnect.getConnection();
			PreparedStatement ps;

			try {
				ps = connect.prepareStatement(query.toString());
				ResultSet rs = ps.executeQuery();
				rs.next();
				company = new Company();
				company.setId(id);
				company.setName(rs.getString(1));
				connect.close();
			} catch (SQLException e) {
				System.out.println("Company DAO says : getCompanyById " + e.getMessage());
			}
		}
		return company;
	}

}
