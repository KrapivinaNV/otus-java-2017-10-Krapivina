package otus.service.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.Future;

public class Main {

    private static final String WEBSOCKET_URL = "ws://localhost:8080/hw/my-web-socket-servlet";

    public static void main(String[] args) throws Exception {
        URI uri = URI.create(WEBSOCKET_URL);

        WebSocketClient client = new WebSocketClient();
        MyClientWebSocket myClientWebSocket = new MyClientWebSocket();

        try {
            client.start();

            Future<Session> sessionFuture = client.connect(myClientWebSocket, uri);
            sessionFuture.get();

            System.out.println("Client:: Data has been sent");
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        } finally {
            client.stop();
        }
    }

}
