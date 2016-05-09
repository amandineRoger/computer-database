package com.excilys.cdb.util;

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
