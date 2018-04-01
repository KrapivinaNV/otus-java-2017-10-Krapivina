package otus.myorm;

import otus.messageSystem.Address;
import otus.messageSystem.MessageSystem;
import otus.messageSystem.MessageSystemContext;
import otus.service.DBService;
import otus.data.AddressDataSet;
import otus.data.DataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @deprecated
 * hand-made ORM implementation
 */
@Deprecated
public class DBServiceImpl implements DBService {

    private final Connection CONNECTION = ConnectionHelper.getConnection();

    private final Address address;
    private final MessageSystemContext context;

    public DBServiceImpl(Address address, MessageSystemContext context) throws SQLException {
        this.address = address;
        this.context = context;

        UsersDAO usersDAO = new UsersDAO(CONNECTION);
        usersDAO.prepareTables(AddressDataSet.class);
        usersDAO.prepareTables(UserDataSet.class);
        usersDAO.prepareTables(PhoneDataSet.class);

        init();
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);
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
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
