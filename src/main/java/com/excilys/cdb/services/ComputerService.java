package com.excilys.cdb.services;

import java.time.LocalDate;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.util.UtilDate;

public enum ComputerService {
    INSTANCE;

    private static ComputerDAO computerDAO;
    private Page<Computer> currentPage;

    /**
     * private constructor for ComputerService (Singleton pattern).
     */
    static {
        computerDAO = ComputerDAO.INSTANCE;
    }

    /**
     * Get the first page of computers.
     *
     * @return computers from 1 to Page.NUMBER_OF_RESULTS;
     */
    public Page<Computer> getComputerList() {
        currentPage = new Page<>(computerDAO.getCount());
        getPageContent();
        return currentPage;
    }

    /**
     * Get the next page of results.
     *
     * @return the next Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Computer> getNextPage() {
        currentPage.next();
        getPageContent();
        return currentPage;
    }

    /**
     * Get the previous page of results.
     *
     * @return the previous Page.NUMBER_OF_RESULTS Companies
     */
    public Page<Computer> getPreviousPage() {
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
        currentPage.setList(computerDAO.getComputerList(i * limit, limit));
    }

    public Page<Computer> getCurrentPage() {
        return this.currentPage;
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
     * @param computer
     *            computer entity which represents the computer to insert in DB
     *
     * @return created computer
     */
    public Computer createComputer(Computer computer) {
        return computerDAO.createComputer(computer);
    }

    /**
     * Update a computer (user choice).
     *
     * @param computer
     *            the updated computer (Caution! update is based on computer.id)
     *
     * @return updated computer
     */
    public Computer updateComputer(Computer computer) {
        return computerDAO.updateComputer(computer);
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

    /**
     * Get the number of computers in database.
     *
     * @return number of computers in database
     */
    public long getCount() {
        return computerDAO.getCount();
    }

    /**
     * Back validation to add or update a computer in DB.
     *
     * @param computer
     *            the computer to test
     * @return true if the computer is valid, false else
     */
    public boolean validateComputer(Computer computer) {
        boolean check = true;
        // Validate name
        String name = computer.getName();
        if (name == null || name.equals("")) {
            check = false;
        }

        // validate dates
        LocalDate intro = computer.getIntroduced();
        LocalDate disco = computer.getDiscontinued();

        if (intro != null && disco != null) {
            if (!UtilDate.checkDates(intro, disco)) {
                check = false;
            }
        } else {
            if (intro != null) {
                if (!UtilDate.checkDBCompat(intro)) {
                    check = false;
                }
            }
            if (disco != null) {
                if (!UtilDate.checkDBCompat(disco)) {
                    check = false;
                }
            }
        }
        return check;
    }

}
