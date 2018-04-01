package otus.service.db.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import otus.cache.CacheEngine;
import otus.cache.MyElement;
import otus.data.DataSet;
import otus.messageSystem.Address;
import otus.messageSystem.Message;
import otus.messageSystem.MessageSystem;
import otus.messageSystem.MessageSystemContext;
import otus.service.DBService;

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private CacheEngine<Long, DataSet> cacheEngineBuilder;

    private final Address address;
    private final MessageSystemContext context;

    public DBServiceHibernateImpl(Configuration configuration, CacheEngine<Long, DataSet> cacheBuilder, Address address, MessageSystemContext context) {

        sessionFactory = configuration.buildSessionFactory();
        if (cacheBuilder != null) {
            cacheEngineBuilder = cacheBuilder;
        }

        this.address = address;
        this.context = context;
        init();
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
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

    @Override
    public void getCacheParams() {
        Message message = new GetCacheParamsResultMsg(
                getAddress(),
                context.getFrontAddress(),
                cacheEngineBuilder.getHitCount(),
                cacheEngineBuilder.getMissCount(),
                cacheEngineBuilder.getGCMissCount()
        );

        context.getMessageSystem().sendMessage(message);
    }

    private void saveInCache(Long id, DataSet object) {
        MyElement<Long, DataSet> myElement = new MyElement<>(id, object);
        cacheEngineBuilder.put(myElement);
    }
}