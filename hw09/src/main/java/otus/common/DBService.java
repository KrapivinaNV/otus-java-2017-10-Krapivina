package otus.common;

import java.sql.SQLException;
import otus.data.DataSet;

public interface DBService {
    public <T extends DataSet> void save(T user) throws SQLException;
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;


}
