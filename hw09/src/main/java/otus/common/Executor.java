package otus.common;

import com.google.common.collect.Sets;
import org.h2.tools.Server;
import org.hibernate.cfg.Configuration;
import otus.data.AddressDataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;
import otus.hibernate.DBServiceHibernateImpl;
import otus.myorm.DBServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Executor {

    public static void main(String[] args) {

        Server server = null;
        try {
            server = Server.createWebServer().start(); // web console http://localhost:8082/
                System.out.println("My ORM implementation:");
                 myORMServiceImpl();

            System.out.println("\nMy Hibernate implementation:");
            myHibernateTest();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (server != null && server.isRunning(false)) {
                //server.stop();
            }
        }
    }

    static void myORMServiceImpl() throws SQLException {

        DBServiceImpl dbService = new DBServiceImpl();

        UserDataSet user1 = new UserDataSet("User1", 27, new AddressDataSet("street1"));
        UserDataSet user2 = new UserDataSet("User2", 31, new AddressDataSet("street2"));
        UserDataSet user3 = new UserDataSet("User3", 35, new AddressDataSet("street3"));

        dbService.save(user1);
        dbService.save(user2);
        dbService.save(user3);

        System.out.println(dbService.load(user1.getId(), UserDataSet.class).toString());
        System.out.println(dbService.load(user3.getId(), UserDataSet.class).toString());
    }

    private static void myHibernateTest() throws SQLException, IOException {

        PropertiesLoader propertiesLoader = new PropertiesLoader();

        /////////////////// config loader //////////////////////

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);

        Properties properties = propertiesLoader.loadProperties();

        /////////////////////////////

        properties.stringPropertyNames()
                .forEach(property -> configuration.setProperty(property, properties.getProperty(property)));

        DBService dbServiceHibernate = new DBServiceHibernateImpl(configuration);

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

            phone1.setUser(user10);
            phone2.setUser(user10);
            phone3.setUser(user11);

            dbServiceHibernate.save(user10);
            dbServiceHibernate.save(user11);

            System.out.println(dbServiceHibernate.load(user10.getId(), UserDataSet.class));
            System.out.println(dbServiceHibernate.load(user11.getId(), UserDataSet.class));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //dbServiceHibernate.shutdown();
        }
    }
}
