package mapper.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBMapper<T> {
    T fromResult(ResultSet rs) throws SQLException;
    void toInsertStmt(PreparedStatement stmt, T entity) throws SQLException;
    void toUpdateStmt(PreparedStatement stmt, T entity) throws SQLException;
}
