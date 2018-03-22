package otus.service.frontend;

import otus.service.msg.AddUserMsg;
import otus.messageSystem.Address;
import otus.messageSystem.Message;
import otus.messageSystem.MessageSystem;
import otus.messageSystem.MessageSystemContext;

import java.util.HashMap;
import java.util.Map;

public class FrontendServiceImpl implements FrontendService {

    private final Address address;
    private final MessageSystemContext context;

    private final Map<Integer, String> users = new HashMap<>();

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;

        init();
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }


    @Override
    public void handleRequest(String name, Number age, String address, String numberPhone) {
        Message message = new AddUserMsg(getAddress(), context.getDbAddress(), name, age, address, numberPhone);
        context.getMessageSystem().sendMessage(message);

    }

    @Override
    public void addUser(int id, String name) {

    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
