package com.excilys.cdb.control;

import java.util.List;

public class Page<T> {
    public static final int LIMIT = 10;
    private int nbResults;

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

    /**
     * increment pageNumber.
     */
    public void next() {
        this.pageNumber++;
    }

    /**
     * decrement pageNumber.
     */
    public void previous() {
        this.pageNumber--;
    }
}
