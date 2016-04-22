package util;

import java.sql.Timestamp;
import java.time.LocalDate;

public class UtilDate {

	public static final int CURRENT_YEAR = LocalDate.now().getYear();

	public static final String NULL_TIMESTAMP = "0000-00-00";
	
	/**
	 * Convert sql.Timestamp into time.LocalDate
	 * 
	 * @param ts
	 *            sql.Timestamp to convert
	 * @return LocalDate
	 */
	public static LocalDate timeStampToLocalDate(Timestamp ts) {
		LocalDate date;

		if (ts == null) {
			date = null;
		} else {
			date = ts.toLocalDateTime().toLocalDate();
		}

		return date;
	}
}
