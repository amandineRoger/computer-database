package com.excilys.cdb.services;

import java.time.LocalDate;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.util.UtilDate;

public enum ComputerService {
    INSTANCE;

    private static ComputerDAO computerDAO;
    private int nbItem;

    public int getNbItem() {
        return nbItem;
    }

    /**
     * private constructor for ComputerService (Singleton pattern).
     */
    static {
        computerDAO = ComputerDAO.INSTANCE;
        INSTANCE.nbItem = computerDAO.getCount();
    }

    /**
     * Get the first page of computers.
     *
     * @param pageNumber
     *            number of the page to construct
     * @param limit
     *            number of item in the page
     * @return computers from pageNumber*limit to (pageNumber+1)*limit;
     */

    public Page<Computer> getComputerList(int pageNumber, int limit) {
        Page.Builder<Computer> pageBuilder = new Page.Builder<Computer>(nbItem)
                .pageNumber(pageNumber).limit(limit);
        Page<Computer> page = pageBuilder.build();
        setPageContent(page);
        return page;
    }

    /**
     * fill the page list of computer.
     *
     * @param page
     *            the page to fill
     */
    public void setPageContent(Page<Computer> page) {
        int i = page.getPageNumber();
        int limit = page.getLimit();
        page.setList(computerDAO.getComputerList(i * limit, limit));
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
        nbItem++;
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
        nbItem--;
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
