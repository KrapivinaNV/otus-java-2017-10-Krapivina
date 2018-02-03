package otus.myorm;

import otus.common.DBService;
import otus.data.AddressDataSet;
import otus.data.DataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {

    private final Connection CONNECTION = ConnectionHelper.getConnection();

    public DBServiceImpl() throws SQLException {
        UsersDAO usersDAO = new UsersDAO(CONNECTION);
        usersDAO.prepareTables(AddressDataSet.class);
        usersDAO.prepareTables(UserDataSet.class);
        usersDAO.prepareTables(PhoneDataSet.class);
    }

    @Override
    public <T extends DataSet> void save(T object) throws SQLException {
        UsersDAO usersDAO = new UsersDAO(CONNECTION);
        usersDAO.save(object);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        UsersDAO usersDAO = new UsersDAO(CONNECTION);
        T user = usersDAO.load(id, clazz);
        return user;
    }

    @Override
    public void shutdown() {

    }
}
