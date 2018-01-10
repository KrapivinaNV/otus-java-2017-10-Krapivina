package otus.common;

import otus.data.DataSet;

import java.sql.SQLException;

public interface DBService {
    <T extends DataSet> void save(T object) throws SQLException;
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    void shutdown();
}
