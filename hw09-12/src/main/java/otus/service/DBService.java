package otus.service;

import otus.data.DataSet;
import otus.messageSystem.Address;
import otus.messageSystem.Addressee;
import otus.messageSystem.MessageSystem;

import java.sql.SQLException;

public interface DBService extends Addressee {
    <T extends DataSet> void save(T object) throws SQLException;
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    Address getAddress();
    MessageSystem getMS();

    void shutdown();
}
