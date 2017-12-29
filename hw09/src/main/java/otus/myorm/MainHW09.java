package otus.myorm;

import org.h2.tools.Server;

import java.sql.SQLException;

public class MainHW09 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Server server = null;
        try {
            server = Server.createWebServer().start(); // web console http://localhost:8082/

            UserDataSet user1 = new UserDataSet("User1", 27);
            UserDataSet user2 = new UserDataSet("User2", 31);
            UserDataSet user3 = new UserDataSet("User3", 35);
            UserDataSet user4 = new UserDataSet("User4", 15);

            Executor.save(user1);
            Executor.save(user2);
            Executor.save(user3);
            Executor.save(user4);

           System.out.println(Executor.load(user1.getId(), UserDataSet.class).toString());
           System.out.println(Executor.load(user3.getId(), UserDataSet.class).toString());

        } finally {
            if (server != null) {
                server.stop();
            }
        }
    }
}
