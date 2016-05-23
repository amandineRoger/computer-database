package com.excilys.cdb.util;

import javax.servlet.http.HttpServletRequest;

public class PageRequest {
    int pageNumber = 0;
    int limit = 10;
    String search;
    Sort sorting;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Sort getSorting() {
        return sorting;
    }

    /**
     * set a Sort object created from String parameters.
     *
     * @param field
     *            the name of the column to order by
     * @param ascendant
     *            null, "ASC" or "true" for ascendant direction
     */
    public void setSorting(String field, String ascendant) {
        Sort sort = new Sort.SortBuilder(field).direction(ascendant).build();
        this.sorting = sort;
    }

    /**
     * Page request constructor: parse an HttpServletRequest and create a
     * PageRequest.
     *
     * @param request
     *            the HttpServletRequest to parse
     */
    public PageRequest(HttpServletRequest request) {
        search = request.getParameter("search");
        search = search == null ? "" : search;
        setSorting(request.getParameter("order"), request.getParameter("asc"));

        String paramLimit = request.getParameter("limit");
        if (paramLimit != null
                && paramLimit.matches(com.excilys.cdb.validators.Constants.REGEX_INT)) {
            limit = Integer.parseInt(paramLimit);
        }
        String paramPage = request.getParameter("page");
        if (paramPage != null
                && paramPage.matches(com.excilys.cdb.validators.Constants.REGEX_INT)) {
            pageNumber = Integer.parseInt(paramPage);
        }
    }
}
