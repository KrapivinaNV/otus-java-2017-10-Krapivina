package otus.common;

import com.google.common.collect.Sets;
import org.h2.tools.Server;
import otus.data.AddressDataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;
import otus.hibernate.ConfigurationLoader;
import otus.hibernate.DBServiceHibernateImpl;
import otus.hibernate.PropertiesLoader;
import otus.myorm.DBServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class Executor {

    public static void main(String[] args) {

        Server server = null;
        try {
            server = Server.createWebServer().start(); // web console http://localhost:8082/

            System.out.println("My ORM test:");
            myORMTest();

            System.out.println("\nMy Hibernate test:");
            myHibernateTest();

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

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        ConfigurationLoader configurationLoader = new ConfigurationLoader(propertiesLoader);
        DBService dbServiceHibernate = new DBServiceHibernateImpl(configurationLoader.getConfiguration());

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
}
