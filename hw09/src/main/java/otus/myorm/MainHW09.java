package otus.myorm;

import org.h2.tools.Server;

import java.sql.SQLException;

public class MainHW09 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Server server = null;

        try {
            server = Server.createWebServer().start(); // http://localhost:8082/

            Executor.save(new UserDataSet(1, "User1", 27));
            Executor.save(new UserDataSet(2, "User2", 31));

            System.out.println(Executor.load(1, UserDataSet.class).toString());
            System.out.println(Executor.load(2, UserDataSet.class).toString());

        } finally {
            if (server != null) {
                server.shutdown();
            }
        }
    }
}
