package com.excilys.cdb.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.util.PageRequest;
import com.excilys.cdb.util.Sort;

@Service("computerService")
@Scope("singleton")
public class ComputerService {
    @Autowired
    @Qualifier("computerDAO")
    private ComputerDAO computerDAO;
    private long nbItem;

    public long getNbItem() {
        return nbItem;
    }

    @PostConstruct
    public void postConstructInit() {
        nbItem = getCount();
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
     * Search computers by name.
     *
     * @param search
     *            the name to search
     * @param offset
     *            offset from which start to search
     * @param limit
     *            number of results
     * @param order
     *            column filter
     * @param asc
     *            direction of order; true if ascendant, false if descendant
     * @return all computers which name contains search
     */
    public Page<Computer> searchByName(String search, int offset, int limit,
            String order, String asc) {
        List<Computer> computers = computerDAO.findByName(search, offset, limit,
                order, asc);
        Page.Builder<Computer> builder = new Page.Builder<Computer>(
                computerDAO.getSearchCount(search)).limit(limit);

        Page<Computer> page = builder.build();
        page.setList(computers);

        return page;
    }

    /**
     * Get the number of results of search.
     *
     * @param search
     *            search parameter
     * @return the number of results
     */
    public int getSearchedCount(String search) {
        return computerDAO.getSearchCount(search);
    }

    /**
     * Construct and return a page based on PageRequest.
     *
     * @param request
     *            a PageRequest
     * @return a page filled with request results
     */
    public Page<Computer> getPage(PageRequest request) {
        int limit = request.getLimit();
        int pageNumber = request.getPageNumber();
        int offset = pageNumber * limit;
        Sort sort = request.getSorting();
        Page<Computer> page = searchByName(request.getSearch(), offset, limit,
                sort.getField(), sort.getDirection());
        page.setPageNumber(pageNumber);
        return page;
    }

}
