package main.java.com.excilys.cdb.util;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface UtilDate {
    /**
     * Constant for insert null timestamp in db.
     */
    String NULL_TIMESTAMP = "0000-00-00";

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
}
