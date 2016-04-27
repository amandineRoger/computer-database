package com.excilys.cdb.control;

import java.util.List;

public class Page<T> {
    private int limit = 10;
    private int nbResults;
    private int nbPages;

    private int pageNumber;
    private List<T> list;

    /**
     * Constructor of a page, initialize at first page.
     *
     * @param count
     *            number of results of count request
     */
    public Page(int count) {
        this.pageNumber = 0;
        this.nbResults = count;
        updateNbPages();
    }

    public int getNbResults() {
        return nbResults;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getLimit() {
        return this.limit;
    }

    /**
     * change the number of results per page.
     *
     * @param limit
     *            number of results
     */
    public void setLimit(int limit) {
        this.limit = limit;
        updateNbPages();
    }

    /**
     * increment pageNumber.
     */
    public void next() {
        if (pageNumber < nbPages) {
            pageNumber++;
        }

    }

    /**
     * decrement pageNumber.
     */
    public void previous() {
        if (pageNumber > 0) {
            this.pageNumber--;
        }

    }

    /**
     * Calculate the number of pages required to display all results.
     */
    private void updateNbPages() {
        this.nbPages = (int) Math.ceil(nbResults / limit);
    }

}
