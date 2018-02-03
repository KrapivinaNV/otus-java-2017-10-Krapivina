package otus;

import com.google.common.collect.Sets;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.h2.tools.Server;
import otus.cache.CacheConfig;
import otus.cache.CacheEngineImpl;
import otus.cache.CacheHolder;
import otus.cache.MyCache;
import otus.common.DBService;
import otus.data.AddressDataSet;
import otus.data.DataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;
import otus.hibernate.ConfigurationLoader;
import otus.hibernate.DBServiceHibernateImpl;
import otus.jetty.AdminServlet;
import otus.jetty.LoginServlet;
import otus.myorm.DBServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class MyExecutor {

    private static final String PUBLIC_HTML = "public_html";

    public static void main(String[] args) {

        Server server = null;
        try {
            server = Server.createWebServer().start(); // web console http://localhost:8082/

            // System.out.println("My ORM test:");
            // myORMTest();

            // System.out.println("\nMy Hibernate test:");
            // myHibernateTest();

            // myCacheTest(); // -Xms8m -Xmx8m

            myServletsTest();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (server != null && server.isRunning(false)) {
                //server.stop();
            }
        }
    }

    static void myORMTest() throws SQLException {

        DBService dbService = new DBServiceImpl();

        PhoneDataSet phone1 = new PhoneDataSet("890234467676");
        PhoneDataSet phone2 = new PhoneDataSet("11111111111");
        PhoneDataSet phone3 = new PhoneDataSet("22222222222");

        UserDataSet user1 = new UserDataSet("User1", 27, new AddressDataSet("street1"), Sets.newHashSet(phone1, phone2));
        UserDataSet user2 = new UserDataSet("User2", 31, new AddressDataSet("street2"), Sets.newHashSet(phone3));
        UserDataSet user3 = new UserDataSet("User3", 31, null, null);

        dbService.save(user1);
        dbService.save(user2);
        dbService.save(user3);

        System.out.println(dbService.load(user1.getId(), UserDataSet.class).toString());
        System.out.println(dbService.load(user2.getId(), UserDataSet.class).toString());
        System.out.println(dbService.load(user3.getId(), UserDataSet.class).toString());
    }

    private static void myHibernateTest() throws SQLException, IOException {

        MyCache<Long, DataSet> cacheEngine = CacheHolder.getMyCache();

        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        DBService dbServiceHibernate = new DBServiceHibernateImpl(configurationLoader.getConfiguration(), cacheEngine);

        try {
            PhoneDataSet phone1 = new PhoneDataSet("890234467676");
            PhoneDataSet phone2 = new PhoneDataSet("11111111111");
            PhoneDataSet phone3 = new PhoneDataSet("22222222222");

            UserDataSet user10 = new UserDataSet(
                    "User10",
                    27,
                    new AddressDataSet("street1"),
                    Sets.newHashSet(phone1, phone2)
            );
            UserDataSet user11 = new UserDataSet(
                    "User11",
                    10,
                    new AddressDataSet("street2"),
                    Sets.newHashSet(phone3)
            );
            UserDataSet user12 = new UserDataSet(
                    "User12",
                    12,
                    null,
                    null
            );

            phone1.setUser(user10);
            phone2.setUser(user10);
            phone3.setUser(user11);

            dbServiceHibernate.save(user10);
            dbServiceHibernate.save(user11);
            dbServiceHibernate.save(user12);

            System.out.println(dbServiceHibernate.load(user10.getId(), UserDataSet.class));
            System.out.println(dbServiceHibernate.load(user11.getId(), UserDataSet.class));
            System.out.println(dbServiceHibernate.load(user12.getId(), UserDataSet.class));


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //dbServiceHibernate.shutdown();
        }
    }

    private static void myCacheTest() throws SQLException, IOException {

        MyCache<Long, DataSet> cacheEngine = CacheHolder.getMyCache();

        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        DBService dbServiceHibernate = new DBServiceHibernateImpl(configurationLoader.getConfiguration(), cacheEngine);

        try {
            generateUsers(1400, dbServiceHibernate);

            System.out.println("Cache hint count:" + cacheEngine.getHitCount());
            System.out.println("Cache mis count:" + cacheEngine.getMissCount());
            System.out.println("Cache GC mis count:" + cacheEngine.getGCMissCount());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cacheEngine.dispose();
        }
    }


    private static void myServletsTest() throws Exception {

        MyCache<Long, DataSet> cacheEngine = CacheHolder.getMyCache();

        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        DBService dbServiceHibernate = new DBServiceHibernateImpl(configurationLoader.getConfiguration(), cacheEngine);
        try {
            generateUsers(10, dbServiceHibernate);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cacheEngine.dispose();
        }


        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(AdminServlet.class, "/admin");
        context.addServlet(LoginServlet.class, "/login");

        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8090);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }

    private static void generateUsers(int countUsers, DBService dbServiceHibernate) throws SQLException {
        for (int i = 0; i < countUsers; i++) {
            PhoneDataSet phone1 = new PhoneDataSet("11111111111");
            PhoneDataSet phone2 = new PhoneDataSet("22222222222");
            UserDataSet user = new UserDataSet(
                    "User" + i,
                    i,
                    new AddressDataSet("street"),
                    Sets.newHashSet(phone1, phone2)
            );
            phone1.setUser(user);
            phone2.setUser(user);
            dbServiceHibernate.save(user);
        }

        for (int i = 0; i < countUsers; i++) {
            Random random = new Random();
            int index = random.nextInt(countUsers) + 1;
            UserDataSet load = dbServiceHibernate.load(index, UserDataSet.class);
            System.out.println("id = " + index + " Data = " + load);
        }
    }
}