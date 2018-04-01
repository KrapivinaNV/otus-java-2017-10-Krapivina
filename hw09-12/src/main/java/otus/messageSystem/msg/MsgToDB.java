package otus.messageSystem.msg;


import otus.service.DBService;
import otus.messageSystem.Address;
import otus.messageSystem.Addressee;
import otus.messageSystem.Message;

import java.sql.SQLException;

public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) throws SQLException {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService) throws SQLException;
}
