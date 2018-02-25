package otus.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import otus.cache.CacheEngine;
import otus.common.DBService;
import otus.data.DataSet;
import otus.cache.MyElement;

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private CacheEngine<Long, DataSet> cacheEngineBuilder;

    public DBServiceHibernateImpl(Configuration configuration, CacheEngine<Long, DataSet> cacheBuilder) {

        sessionFactory = configuration.buildSessionFactory();
        if (cacheBuilder != null) {
            cacheEngineBuilder = cacheBuilder;
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
        MyElement myElement = cacheEngineBuilder.get(id);
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
        cacheEngineBuilder.put(myElement);
    }
}

