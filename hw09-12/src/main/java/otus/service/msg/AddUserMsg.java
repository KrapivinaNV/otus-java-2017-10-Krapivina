package otus.service.msg;

import com.google.common.collect.Sets;
import otus.service.DBService;
import otus.data.AddressDataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;
import otus.messageSystem.Address;
import otus.messageSystem.msg.MsgToDB;

import java.sql.SQLException;

public class AddUserMsg extends MsgToDB {
    private String name;
    private final Number age;
    private final String address;
    private final String numberPhone;


    public AddUserMsg(Address from, Address to, String name, Number age, String address, String numberPhone) {
        super(from, to);
        this.name = name;
        this.age = age;
        this.address = address;
        this.numberPhone = numberPhone;
    }

    @Override
    public void exec(DBService dbService) throws SQLException {
        PhoneDataSet phone = new PhoneDataSet(numberPhone);
        UserDataSet user = new UserDataSet(
                this.name,this.age,
                new AddressDataSet(address),
                Sets.newHashSet(phone)
        );
        phone.setUser(user);
        dbService.save(user);
    }
}