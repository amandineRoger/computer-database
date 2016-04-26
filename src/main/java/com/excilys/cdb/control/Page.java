package com.excilys.cdb.control;

import java.util.List;

public class Page<T> {
    public static final int NUMBER_OF_RESULTS = 10;

    private int pageNumber;
    private List<T> list;

    public Page() {
        pageNumber = 0;
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

}
