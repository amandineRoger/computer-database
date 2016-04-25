package main.java.com.excilys.cdb.services;

import main.java.com.excilys.cdb.control.Page;
import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.entities.Computer;

public class ComputerService {
    private static ComputerService instance;
    private ComputerDAO computerDAO;
    private Page<Computer> currentPage;

    private ComputerService() {
        computerDAO = ComputerDAO.getInstance();
    }

    public static ComputerService getInstance() {
        if (instance == null) {
            synchronized (ComputerService.class) {
                if (instance == null) {
                    instance = new ComputerService();
                }

            }
        }
        return instance;
    }

    public Page<Computer> getComputerList() {
        currentPage = new Page<>();
        currentPage.setList(
                computerDAO.getComputerList(0, Page.NUMBER_OF_RESULTS));
        return currentPage;
    }

    /**
     * Get the next page of results
     *
     * @return the next Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Computer> getNextPage() {
        int i = currentPage.getPageNumber();
        currentPage.setPageNumber(++i);

        currentPage.setList(computerDAO.getComputerList(
                i * Page.NUMBER_OF_RESULTS, Page.NUMBER_OF_RESULTS));

        return currentPage;
    }

    /**
     * Get the previous page of results
     *
     * @return the previous Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Computer> getPreviousPage() {
        int i = currentPage.getPageNumber();
        currentPage.setPageNumber(--i);

        currentPage.setList(computerDAO.getComputerList(
                i * Page.NUMBER_OF_RESULTS, Page.NUMBER_OF_RESULTS));

        return currentPage;
    }

    /**
     * Find a computer by its id.
     *
     * @param id
     *            the id of the wanted computer
     * @return the wanted computer
     */
    public Computer getComputerDetail(long id) {
        return computerDAO.getComputerDetail(id);
    }

    /**
     * Create a computer from user entry.
     *
     * @return created computer
     */
    public Computer createComputer() {
        return computerDAO.createComputer();
    }

    /**
     * Update a computer (user choice).
     *
     * @return updated computer
     */
    public Computer updateComputer() {
        return computerDAO.updateComputer();
    }

    /**
     * Delete a computer by its id.
     *
     * @param id
     *            id of wanted computer to delete
     * @return deleted computer
     */
    public Computer deleteComputer(long id) {
        return computerDAO.deleteComputer(id);
    }
}
