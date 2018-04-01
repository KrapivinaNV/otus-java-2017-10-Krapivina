package otus.service.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.util.SerializationUtils;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class MyClientWebSocket {

    @SuppressWarnings("unused")
    private Session session;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Client:: Connection closed: %d - %s%n", statusCode, reason);
        this.session = null;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Client:: Got connect: %s%n", session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, byte buf[], int offset, int length) {
        System.out.println("Received from server!!!" + SerializationUtils.deserialize(buf));
    }
}