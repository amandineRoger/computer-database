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

    /**
     * convert a string into a local date.
     *
     * @param str
     *            a date in string
     * @return a local date
     */
    static LocalDate stringToLocalDate(final String str) {
        LocalDate date = null;
        if (!str.equals("")) {
            final DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern(FORMAT_DATE);
            date = LocalDate.parse(str, formatter);
        }
        return date;
    }

    /**
     * Check if discontinued is after introduced and if both dates are
     * compatible with sql.Timestamp.
     *
     * @param introduced
     *            introduced date
     * @param discontinued
     *            discontinued date
     * @return true if dates are valid, false if they are not
     */
    static boolean checkDates(LocalDate introduced, LocalDate discontinued) {
        if (discontinued.isAfter(introduced) && checkDBCompat(introduced)
                && checkDBCompat(discontinued)) {
            return true;
        }
        return false;
    }

    /**
     * check if the date if compatible with sql.Timestamp.
     *
     * @param date
     *            the date to check
     * @return true if date is compatible, false if it is not
     */
    static boolean checkDBCompat(LocalDate date) {
        LocalDate start = LocalDate.of(1970, 01, 01);
        LocalDate end = LocalDate.of(2037, 12, 31);

        if (date.isAfter(start) && date.isBefore(end)) {
            return true;
        }
        return false;
    }
}
