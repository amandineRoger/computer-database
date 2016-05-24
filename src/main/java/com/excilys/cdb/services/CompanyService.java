package com.excilys.cdb.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.entities.Company;

@Service("companyService")
@Transactional
public class CompanyService {

    @Autowired
    @Qualifier("companyDAO")
    private CompanyDAO companyDAO;
    private int nbItems;

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

        try {
            computerDAO.deleteComputersByCompany(id);
            companyDAO.deleteCompany(id);
        } catch (DAOException e) {
            // rollback ? //FIXME
        } finally {
            // set to null for set computerDAO eligible for gc
            computerDAO = null;
        }
    }

}
