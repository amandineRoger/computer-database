package com.excilys.cdb.services;

import java.util.List;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.entities.Company;

public enum CompanyService {
    INSTANCE;

    private static CompanyDAO companyDAO;
    private static Page<Company> currentPage;
    private int nbItems;

    public int getNbItems() {
        return nbItems;
    }

    static {
        companyDAO = CompanyDAO.INSTANCE;
        INSTANCE.nbItems = companyDAO.getCount();
    }

    /**
     * Get paginated results of Company list.
     *
     * @return page which contains Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Company> getCompanyList() {
        // currentPage = new Page<>(companyDAO.getCount());
        getPageContent();
        return currentPage;
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
     * Get the next page of results.
     *
     * @return the next Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Company> getNextPage() {
        currentPage.next();
        getPageContent();
        return currentPage;
    }

    /**
     * Get the previous page of results.
     *
     * @return the previous Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Company> getPreviousPage() {
        currentPage.previous();
        getPageContent();
        return currentPage;
    }

    /**
     * set companies in page list.
     */
    public void getPageContent() {
        int i = currentPage.getPageNumber();
        int limit = currentPage.getLimit();
        currentPage.setList(companyDAO.getCompanyList(i * limit, limit));
    }

    public Page<Company> getCurrentPage() {
        return currentPage;
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
     * Call transaction to delete a company by its id and delete all computers from this company.
     *
     * @param id
     *            id of the company to delete
     */
    public void deleteCompany(long id) {
        companyDAO.deleteCompany(id);
    }

}
