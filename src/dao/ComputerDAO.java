package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import bdd.SingleConnect;
import control.Master;
import entities.Company;
import entities.Computer;
import util.Utils;

public class ComputerDAO {

	private static final String TABLE = "computer";

	private static final int COL_ID = 1;
	private static final int COL_NAME = 2;
	private static final int COL_INTRODUCED = 3;
	private static final int COL_DISCONTINUED = 4;
	private static final int COL_COMPANY_ID = 5;
	private static final int COL_COMPANY_NAME = 6;

	private SingleConnect singleConnect;
	private Connection connect;

	public ComputerDAO() {
		this.singleConnect = SingleConnect.getInstance();
	}

	public ArrayList<Computer> getComputerList() {
		// Query building
		StringBuffer query = new StringBuffer("SELECT * FROM ");
		query.append(TABLE);

		ArrayList<Computer> computers = new ArrayList<>();
		ResultSet results = null;
		connect = singleConnect.getConnection();

		try {
			PreparedStatement ps = connect.prepareStatement(query.toString());

			PreparedStatement ps_company;
			String companyQuery = "SELECT name FROM company WHERE id = ";
			ResultSet company_result;

			results = ps.executeQuery();
			Computer tmp_computer;
			Company tmp_company;
			int companyID;
			// Mapping loop
			while (results.next()) {
				tmp_computer = new Computer();
				tmp_computer.setId(results.getInt(COL_ID));
				tmp_computer.setName(results.getString(COL_NAME));

				companyID = results.getInt(COL_COMPANY_ID);
				if (companyID != 0) {
					// getCompany
					tmp_company = new Company();
					tmp_company.setId(companyID);
					ps_company = connect.prepareStatement(companyQuery + companyID);
					company_result = ps_company.executeQuery();
					company_result.next();
					tmp_company.setName(company_result.getString(1));
					tmp_computer.setCompany(tmp_company);
				}

				tmp_computer.setIntroduced(Utils.timeStampToLocalDate(results.getTimestamp(COL_INTRODUCED)));
				tmp_computer.setDiscontinued(Utils.timeStampToLocalDate(results.getTimestamp(COL_DISCONTINUED)));

				computers.add(tmp_computer);
			}
			connect.close();
		} catch (SQLException e) {
			System.out.println("ComputerDAO says : SQLException ! " + e.getMessage());
		}
		return computers;
	}

	public ArrayList<Computer> getComputerDetail(long id) {
		ArrayList<Computer> computer = new ArrayList<>(1);

		// Query
		StringBuffer query = new StringBuffer();
		query.append("SELECT c.id, c.name, c.introduced, c.discontinued, o.id, o.name FROM ");
		query.append(TABLE);
		query.append(" c LEFT JOIN company o ON c.company_id = o.id WHERE c.id = ");
		query.append(id);

		ResultSet results = null;
		connect = singleConnect.getConnection();

		try {

			PreparedStatement ps = connect.prepareStatement(query.toString());
			results = ps.executeQuery();

			Computer tmp;
			Company tmpCompany;
			long idCompany;
			while (results.next()) {
				tmp = new Computer();
				tmp.setId(results.getLong(COL_ID));
				tmp.setName(results.getString(COL_NAME));

				idCompany = results.getLong(COL_COMPANY_ID);
				if (idCompany != 0) {
					tmpCompany = new Company();
					tmpCompany.setId(idCompany);
					tmpCompany.setName(results.getString(COL_COMPANY_NAME));
					tmp.setCompany(tmpCompany);
				}

				tmp.setIntroduced(Utils.timeStampToLocalDate(results.getTimestamp(COL_INTRODUCED)));
				tmp.setDiscontinued(Utils.timeStampToLocalDate(results.getTimestamp(COL_DISCONTINUED)));

				computer.add(tmp);
			}
			connect.close();
		} catch (SQLException e) {
			System.out.println("ComputerDAO says : SQLException ! " + e.getMessage());
		}

		return computer;
	}

	private static final String NULL_TIMESTAMP = "0000-00-00";

	public ArrayList<Computer> createComputer() {
		ArrayList<Computer> list = new ArrayList<>(1);

		Computer computer = Master.getComputerFromUser();

		// Query
		StringBuffer buffer = new StringBuffer("INSERT INTO ");
		buffer.append(TABLE);
		buffer.append(" (name, introduced, discontinued, company_id ) VALUES ( '");
		buffer.append(computer.getName());
		buffer.append("' , '");
		LocalDate tmp = computer.getIntroduced();
		if (tmp != null) {
			buffer.append(tmp);
		} else {
			buffer.append(NULL_TIMESTAMP);
		}
		buffer.append("' , '");
		tmp = computer.getDiscontinued();
		if (tmp != null) {
			buffer.append(tmp);
		} else {
			buffer.append(NULL_TIMESTAMP);
		}
		buffer.append("' , ");
		Company company = computer.getCompany();
		if (company != null) {
			buffer.append("'");
			buffer.append(company.getId());
			buffer.append("'");
		} else {
			buffer.append("NULL");
		}
		buffer.append(")");

		connect = singleConnect.getConnection();
		try {
			// Query execution
			PreparedStatement ps = connect.prepareStatement(buffer.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			computer.setId(rs.getLong(1));

			list.add(computer); // FIXME recup nouveau depuis bdd
			connect.close();
		} catch (SQLException e) {
			System.out.println("ComputerDAO says : SQLException ! " + e.getMessage());
		}

		return list;
	}

	public ArrayList<Computer> updateComputer() {
		ArrayList<Computer> list = new ArrayList<>(1);

		Computer computer = Master.getComputerUpdateFromUser();

		// QUERY
		StringBuffer buffer = new StringBuffer("UPDATE ");
		buffer.append(TABLE);
		buffer.append(" SET name = '");
		buffer.append(computer.getName());
		buffer.append("' ");
		LocalDate date = computer.getIntroduced();
		if (date != null) {
			buffer.append(", introduced = '");
			buffer.append(computer.getIntroduced());
			buffer.append("'");
		}
		date = computer.getDiscontinued();
		if (date != null) {
			buffer.append(", discontinued = '");
			buffer.append(computer.getDiscontinued());
			buffer.append("'");
		}
		Company company = computer.getCompany();
		if (company != null) {
			buffer.append(", company_id = ");
			buffer.append(computer.getCompany().getId());
		}
		buffer.append(" WHERE id = ");
		buffer.append(computer.getId());

		// Query execution
		connect = singleConnect.getConnection();
		try {
			PreparedStatement ps = connect.prepareStatement(buffer.toString());
			ps.executeUpdate();
			list.add(computer);
			connect.close();
		} catch (SQLException e) {
			System.out.println("ComputerDAO says : updateComputer " + e.getMessage());
		}

		return list;
	}

	public ArrayList<Computer> deleteComputer(long id) {
		ArrayList<Computer> computer = getComputerDetail(id);
		
		//QUERY
		StringBuffer buffer = new StringBuffer("DELETE FROM ");
		buffer.append(TABLE);
		buffer.append(" WHERE id = ");
		buffer.append(id);
		
		try {
			connect = singleConnect.getConnection();
			PreparedStatement ps = connect.prepareStatement(buffer.toString());
			ps.executeUpdate();
			connect.close();
		} catch (SQLException e) {
			System.out.println("ComputerDAO says: deleteComputer " + e.getMessage());
		}
		return computer;
	}
}