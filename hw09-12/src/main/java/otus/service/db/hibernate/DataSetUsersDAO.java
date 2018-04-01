package otus.service.db.hibernate;

import org.hibernate.Session;
import otus.data.DataSet;

import java.io.Serializable;

class DataSetUsersDAO {

    private final Session session;

    DataSetUsersDAO(Session session) {
        this.session = session;
    }

    <T extends DataSet> void save(T user) {
        Serializable newDataSet = session.save(user);
        user.setId(Long.parseLong(newDataSet.toString()));
    }

    <T extends DataSet> T load(long id, Class<T> clazz) {
        return session.load(clazz, id);
    }
}
