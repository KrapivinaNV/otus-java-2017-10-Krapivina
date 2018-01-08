package otus.myorm;

import java.sql.Connection;
import java.sql.SQLException;
import otus.common.DBService;
import otus.data.DataSet;

public class DBServiceImpl implements DBService {

    private final Connection CONNECTION = ConnectionHelper.getConnection();

    @Override
    public <T extends DataSet> void save(T user) throws SQLException {
        UsersDAO usersDAO = new UsersDAO(CONNECTION);
        usersDAO.save(user);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        UsersDAO usersDAO = new UsersDAO(CONNECTION);
        T user = usersDAO.load(id, clazz);
        return user;

    }

}
