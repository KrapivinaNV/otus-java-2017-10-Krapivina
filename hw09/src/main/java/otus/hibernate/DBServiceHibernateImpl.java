package otus.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import otus.common.DBService;
import otus.data.DataSet;

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public <T extends DataSet> void save(T object) {
        try (Session session = sessionFactory.openSession()) {
            DataSetUsersDAO dataSetUsersDAO = new DataSetUsersDAO(session);
            dataSetUsersDAO.save(object);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        T dataSet;
        try (Session session = sessionFactory.openSession()) {
            DataSetUsersDAO dataSetUsersDAO = new DataSetUsersDAO(session);
            dataSet = dataSetUsersDAO.load(id, clazz);
        }
        return dataSet;
    }

    @Override
    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}

