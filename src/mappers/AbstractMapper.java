package mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface AbstractMapper<T> {

	public List<T> convertResultSet(ResultSet rs);
	public T convertIntoEntity(ResultSet rs);
	
	public void attachEntityToRequest(PreparedStatement ps, T entity, boolean hasToBeCreated);
}
