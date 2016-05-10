package com.excilys.cdb.services;

import java.sql.Connection;
import java.util.List;

import com.excilys.cdb.control.ConnectionManager;
import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.entities.Company;

public enum CompanyService {
    INSTANCE;

    private static CompanyDAO companyDAO;
    private int nbItems;
    private static ConnectionManager manager;

    public int getNbItems() {
        return nbItems;
    }

    static {
        companyDAO = CompanyDAO.INSTANCE;
        INSTANCE.nbItems = companyDAO.getCount();
        manager = ConnectionManager.INSTANCE;
    }

    /**
     * Get all companies.
     *
     * @return List of all companies in DB
     */
    public List<Company> getAllCompanies() {
        List<Company> companies = companyDAO.getAllCompanyList();
        return companies;
    }

    /**
     * find a company by its id.
     *
     * @param id
     *            id of wanted company
     * @return wanted company
     */
    public Company getCompanyById(long id) {
        return companyDAO.getCompanyById(id);
    }

    /**
     * Get the number of companies in database.
     *
     * @return number of companies in database
     */
    public long getCount() {
        return companyDAO.getCount();
    }

    /**
     * Call transaction to delete a company by its id and delete all computers
     * from this company.
     *
     * @param id
     *            id of the company to delete
     */
    public void deleteCompany(long id) {
        manager.init();
        Connection connect = manager.get();
        try {
            ComputerDAO computerDAO = ComputerDAO.INSTANCE;
            computerDAO.deleteComputersByCompany(id, connect);
            companyDAO.deleteCompany(id, connect);
            manager.commit();
        } catch (DAOException e) {
            manager.rollback();
        } finally {
            manager.close();
        }
    }

}
