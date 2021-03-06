package com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Errors extends HashMap<String, List<String>> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "computerID";
    public static final String NAME = "computerName";
    public static final String INTRODUCED = "introduced";
    public static final String DISCONTINUED = "discontinued";
    public static final String COMPANY = "company";

    /**
     * Return the list corresponding to the key in Errors Object.
     *
     * @param key
     *            the key in the Errors Object for the wanted list
     * @return the wanted list
     */
    public List<String> getListForKey(String key) {
        if (this.containsKey(key)) {
            return this.get(key);
        } else {
            List<String> list = new ArrayList<>();
            this.put(key, list);
            return list;

        }
    }

}
