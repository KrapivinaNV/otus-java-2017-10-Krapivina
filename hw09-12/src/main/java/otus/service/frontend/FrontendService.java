package otus.service.frontend;

import otus.messageSystem.Addressee;

public interface FrontendService extends Addressee {
    void init();

    void addUser(int id, String name);

    void handleRequest(String name, Number age, String address, String numberPhone);

}
