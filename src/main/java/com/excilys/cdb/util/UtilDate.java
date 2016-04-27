package com.excilys.cdb.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface UtilDate {
    /**
     * Constant for insert null timestamp in db.
     */
    String NULL_TIMESTAMP = "0000-00-00";
    String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * Convert sql.Timestamp into time.LocalDate.
     *
     * @param ts
     *            sql.Timestamp to convert
     * @return LocalDate
     */
    static LocalDate timeStampToLocalDate(Timestamp ts) {
        LocalDate date;

        if (ts == null) {
            date = null;
        } else {
            date = ts.toLocalDateTime().toLocalDate();
        }

        return date;
    }

    static LocalDate StringToLocalDate(final String str) {
        LocalDate date = null;
        if (!str.equals("")) {
            final DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern(FORMAT_DATE);
            date = LocalDate.parse(str, formatter);
        }
        return date;
    }
}
