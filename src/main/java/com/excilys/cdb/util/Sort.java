package com.excilys.cdb.util;

public class Sort {
    private String field;
    private String direction;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Tell if sort is in ascendant direction.
     *
     * @return true if sort is in ascendant direction, false if it is in
     *         descendant direction
     */
    public boolean isAscendant() {
        if (direction.equals("ASC")) {
            return true;
        }
        return false;
    }

    /**
     * Sort builder.
     *
     * @author Amandine Roger
     *
     */
    public static class SortBuilder {
        private String field;
        private String direction;

        /**
         * Builder constructor with field name.
         *
         * @param field
         *            the name of the column to order by
         */
        public SortBuilder(String field) {
            if (field == null) {
                field = "";
            }
            switch (field) {
                case "name":
                    this.field = "c.name";
                    break;
                case "introduced":
                    this.field = "c.introduced";
                    break;
                case "discontinued":
                    this.field = "c.discontinued";
                    break;
                case "company_id":
                    this.field = "o.id";
                    break;
                case "id":
                default:
                    this.field = "c.id";
                    break;
            }
        }

        /**
         * Setter for direction of sorting.
         *
         * @param direction
         *            "ASC" or "true" for ascendant
         * @return the same builder object
         */
        public SortBuilder direction(String direction) {
            if (direction == null || direction.equals("true")
                    || direction.equalsIgnoreCase("ASC")) {
                this.direction = "ASC";
            } else {
                this.direction = "DESC";
            }
            return this;
        }

        /**
         * Build a new Sort with all parameters set before.
         *
         * @return a Sort object initialized
         */
        public Sort build() {
            return new Sort(this);
        }
    }

    /**
     * Sort constructor from builder.
     *
     * @param builder
     *            the builder from which construct a Sort object
     */
    private Sort(SortBuilder builder) {
        this.field = builder.field;
        this.direction = builder.direction;
    }

}
