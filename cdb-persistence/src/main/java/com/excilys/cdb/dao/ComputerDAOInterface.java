package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerDAOInterface {

    /**
     * Get computers from select request.
     *
     * @param offset
     *            offset in request
     * @param limit
     *            number of results
     * @return computers from offset+1 to offset+limit+1;
     */
    public List<Computer> getComputerList(int offset, int limit);

    /**
     * Find a computer by its id.
     *
     * @param id
     *            the id of the wanted computer
     * @return the wanted computer
     */
    public Computer getComputerDetail(long id);

    /**
     * Create a computer from user entry.@PersistenceContext
     *
     * @param computer
     *            the entity newly created
     *
     * @return created computer
     */
    public Computer createComputer(Computer computer);

    /**
     * Update a computer (user choice).
     *
     * @param computer
     *            the updated computer (Caution! update is based on computer.id)
     *
     * @return updated computer
     */
    public Computer updateComputer(Computer computer);

    /**
     * Delete a computer by its id.
     *
     * @param id
     *            id of wanted computer to delete
     * @return deleted computer
     */
    public Computer deleteComputer(long id);

    /**
     * Get the number of computers in database.
     *
     * @return number of computers in database
     */
    public long getCount();

    /**
     * Search computers by name.
     *
     * @param search
     *            the name to search
     * @param offset
     *            offset to put in query
     * @param limit
     *            number of results
     * @param order
     *            field used for order results
     * @param asc
     *            true if ascendant order, false else
     * @return all computers which name contains search
     */
    public List<Computer> findByName(String search, int offset, int limit,
            String order, String asc);

    /**
     * Get the number of computers which correspond to search.
     *
     * @param search
     *            search parameter
     * @return the number of computers
     */
    public long getSearchCount(String search);

    /**
     * Delete all computers which are provided by a company.
     *
     * @param companyId
     *            the id of the company
     */
    public void deleteComputersByCompany(long companyId);
}
