package com.excilys.cdb.services;

import java.util.List;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.entities.Company;

public class CompanyService {

    private static CompanyService instance;
    private CompanyDAO companyDAO;
    private Page<Company> currentPage;

    /**
     * private constructor for CompanyService (Singleton pattern).
     */
    private CompanyService() {
        companyDAO = CompanyDAO.getInstance();
    }

    /**
     * getInstance (singleton method).
     *
     * @return the unique instance of CompanyService
     */
    public static CompanyService getInstance() {
        if (instance == null) {
            synchronized (CompanyService.class) {
                if (instance == null) {
                    instance = new CompanyService();
                }
            }
        }
        return instance;
    }

    /**
     * Get paginated results of Company list.
     *
     * @return page which contains Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Company> getCompanyList() {
        currentPage = new Page<>(companyDAO.getCount());
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
        return this.currentPage;
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

}
