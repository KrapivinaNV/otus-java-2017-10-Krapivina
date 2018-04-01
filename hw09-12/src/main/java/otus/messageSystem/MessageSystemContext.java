package otus.messageSystem;


public class MessageSystemContext {

    private final MessageSystem messageSystem;
    private final Address frontAddress;
    private final Address dbAddress;

    public MessageSystemContext(MessageSystem messageSystem, Address frontAddress, Address dbAddress) {
        this.messageSystem = messageSystem;
        this.frontAddress = frontAddress;
        this.dbAddress = dbAddress;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

}
