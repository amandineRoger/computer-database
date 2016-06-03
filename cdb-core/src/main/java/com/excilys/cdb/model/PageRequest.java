package com.excilys.cdb.model;

import java.util.Map;

public class PageRequest {
    public static final String REGEX_INT = "\\d+";
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
     * Page request constructor: parse a requestMap from Controller and create a
     * PageRequest.
     *
     * @param requestMap
     *            the request Map from controller
     */
    public PageRequest(Map<String, String> requestMap) {
        // Search field
        search = requestMap.get("search");
        search = search == null ? "" : search;

        // Sorting fields
        setSorting(requestMap.get("order"), requestMap.get("asc"));

        // Page params fields
        String param = requestMap.get("limit");
        if (param != null && !param.isEmpty() && param.matches(REGEX_INT)) {
            limit = Integer.parseInt(param);
        }

        param = requestMap.get("page");
        if (param != null && !param.isEmpty() && param.matches(REGEX_INT)) {
            pageNumber = Integer.parseInt(param);
        }

    }
}
