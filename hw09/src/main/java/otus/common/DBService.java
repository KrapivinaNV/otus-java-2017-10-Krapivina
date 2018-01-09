package otus.common;

import java.sql.SQLException;
import otus.data.DataSet;

public interface DBService {
    <T extends DataSet> void save(T object) throws SQLException;
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    void shutdown();
}
