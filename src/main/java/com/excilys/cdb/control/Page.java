package com.excilys.cdb.control;

import java.util.List;

public class Page<T> {
    private int limit = 10;
    private long nbResults;
    private int nbPages;
    private int pageNumber = 0;

    public int getNbPages() {
        return nbPages;
    }

    public void setNbResults(long nbResults) {
        this.nbResults = nbResults;
    }

    private List<T> list;

    public long getNbResults() {
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
        this.nbPages = (int) Math.ceil((double) nbResults / (double) limit);
    }

    public static class Builder<T> {
        // params
        private int limit = 10;
        private long nbResults;
        private int pageNumber = 0;

        /**
         * Page Builder constructor.
         *
         * @param nbItem
         *            required parameter to build a page
         */
        public Builder(long nbItem) {
            this.nbResults = nbItem;
        }

        /**
         * setter for limit.
         *
         * @param limit
         *            the number of results per page
         * @return builder instance
         */
        public Builder<T> limit(int limit) {
            this.limit = limit;
            return this;
        }

        /**
         * setter for page number.
         *
         * @param pageNumber
         *            the number of the page to build
         * @return builder instance
         */
        public Builder<T> pageNumber(int pageNumber) {

            this.pageNumber = pageNumber;
            return this;
        }

        /**
         * build a new instance of a page, already fill with right content
         * (depends of pageNumber and Limit).
         *
         * @return instance of Page<T>
         */
        public Page<T> build() {
            return new Page<T>(this);
        }

    }

    /**
     * private constructor, called by builder.
     *
     * @param builder
     *            the builder which call this constructor
     */
    private Page(Builder<T> builder) {
        this.nbResults = builder.nbResults;
        this.limit = builder.limit;
        updateNbPages();
        if (builder.pageNumber > 0 && builder.pageNumber < this.nbPages) {
            this.pageNumber = builder.pageNumber;
        }
    }

}
