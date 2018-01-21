package otus.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import otus.common.DBService;
import otus.data.DataSet;
import otus.hibernate.cache.MyCacheBuilder;
import otus.hibernate.cache.MyElement;

import java.lang.ref.SoftReference;

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private MyCacheBuilder<Long, DataSet> myCacheBuilder;

    public DBServiceHibernateImpl(Configuration configuration, MyCacheBuilder<Long, DataSet> cacheBuilder) {

        sessionFactory = configuration.buildSessionFactory();
        if (cacheBuilder != null) {
            myCacheBuilder = cacheBuilder;
        }
    }

    @Override
    public <T extends DataSet> void save(T object) {
        try (Session session = sessionFactory.openSession()) {
            DataSetUsersDAO dataSetUsersDAO = new DataSetUsersDAO(session);
            dataSetUsersDAO.save(object);
        }
        saveInCache(object.getId(), object);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        T dataSet;
        MyElement myElement = myCacheBuilder.get(id);
        if (myElement != null && myElement.getValue() != null) {
            dataSet = (T) myElement.getValue();
        }
        else{
            try (Session session = sessionFactory.openSession()) {
                DataSetUsersDAO dataSetUsersDAO = new DataSetUsersDAO(session);
                dataSet = dataSetUsersDAO.load(id, clazz);
            }
            saveInCache(id, dataSet);
        }
        return dataSet;
    }


    @Override
    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    private void saveInCache(Long id, DataSet object) {
        MyElement<Long, DataSet> myElement = new MyElement<>(id, object);
        myCacheBuilder.put(myElement);
    }
}

