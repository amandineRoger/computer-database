package com.excilys.cdb.services;

import java.sql.Connection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.cdb.control.ConnectionManager;
import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.entities.Company;

@Service("companyService")
@Scope("singleton")
public class CompanyService {

    @Autowired
    @Qualifier("companyDAO")
    private CompanyDAO companyDAO;
    private int nbItems;
    @Autowired
    @Qualifier("connectionManager")
    private ConnectionManager manager;

    public int getNbItems() {
        return nbItems;
    }

    @PostConstruct
    public void postConstructInit() {
        nbItems = companyDAO.getCount();
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
    @Autowired
    @Qualifier("computerDAO")
    ComputerDAO computerDAO;

    public void deleteCompany(long id) {
        manager.init();
        Connection connect = manager.get();
        try {
            computerDAO.deleteComputersByCompany(id, connect);
            companyDAO.deleteCompany(id, connect);
            manager.commit();
        } catch (DAOException e) {
            manager.rollback();
        } finally {
            manager.close();
            computerDAO = null;
        }
    }

}
