package util;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Utils {

	/*
	 * SQL types found on : 
	 * http://alvinalexander.com/java/edu/pj/jdbc/recipes/ResultSet-ColumnType.shtml
	 */
	public static final int SQLTYPE_NULL = 0;
	public static final int SQLTYPE_BIGINT = -5;
	public static final int SQLTYPE_VARCHAR = 12;
	public static final int SQLTYPE_TIMESTAMP = 93;
	
	public static final int CURRENT_YEAR = LocalDate.now().getYear();
	
	public static LocalDate timeStampToLocalDate(Timestamp ts){
		 LocalDate date;
		 
		 if(ts == null) {
			 date = null;
		 } else {
			 date = ts.toLocalDateTime().toLocalDate();
		 }
		 
		 return date;
	 }
}
