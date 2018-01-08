package otus.common;

import java.sql.SQLException;
import org.h2.tools.Server;
import otus.data.UserDataSet;
import otus.hibernate.DBServiceHibernateImpl;

public class MainHW09 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Server server = null;
        try {
            server = Server.createWebServer().start(); // web console http://localhost:8082/
//            DBServiceImpl executor = new DBServiceImpl();
//
//            UserDataSet user1 = new UserDataSet("User1", 27);
//            UserDataSet user2 = new UserDataSet("User2", 31);
//            UserDataSet user3 = new UserDataSet("User3", 35);
//            UserDataSet user4 = new UserDataSet("User4", 15);
//
//            executor.save(user1);
//            executor.save(user2);
//            executor.save(user3);
//            executor.save(user4);
//
//           System.out.println(executor.load(user1.getId(), UserDataSet.class).toString());
//           System.out.println(executor.load(user3.getId(), UserDataSet.class).toString());

            DBServiceHibernateImpl dbServiceHibernate = new DBServiceHibernateImpl();
            UserDataSet user10 = new UserDataSet("User10", 27);
            dbServiceHibernate.save(user10);
            UserDataSet load = dbServiceHibernate.load(1, UserDataSet.class);


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            if (server != null) {
//                server.stop();
//            }
        }
    }
}
