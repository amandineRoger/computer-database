package com.excilys.cdb.services;

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
     * Get paginated results of Company list
     *
     * @return page which contains Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Company> getCompanyList() {
        currentPage = new Page<>();
        currentPage
                .setList(companyDAO.getCompanyList(0, Page.NUMBER_OF_RESULTS));
        return currentPage;
    }

    /**
     * Get the next page of results
     *
     * @return the next Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Company> getNextPage() {
        int i = currentPage.getPageNumber();
        currentPage.setPageNumber(++i);

        currentPage.setList(companyDAO.getCompanyList(
                i * Page.NUMBER_OF_RESULTS, Page.NUMBER_OF_RESULTS));

        return currentPage;
    }

    /**
     * Get the previous page of results
     *
     * @return the previous Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Company> getPreviousPage() {
        int i = currentPage.getPageNumber();
        currentPage.setPageNumber(--i);

        currentPage.setList(companyDAO.getCompanyList(
                i * Page.NUMBER_OF_RESULTS, Page.NUMBER_OF_RESULTS));

        return currentPage;
    }

    public Company getCompanyById(long id) {
        return companyDAO.getCompanyById(id);

    }

}
