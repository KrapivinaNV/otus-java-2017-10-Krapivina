package otus.service.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.util.SerializationUtils;
import otus.service.frontend.FrontendService;
import otus.service.msg.CacheParams;

import java.nio.ByteBuffer;
import java.util.concurrent.Future;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class MyServerWebSocket {

    private final FrontendService frontendService;

    MyServerWebSocket(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Server:: Connection closed: %d - %s%n", statusCode, reason);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Server:: Got connect: %s%n", session);

        frontendService.sendCacheParamsRequest();

        Future<CacheParams> cacheParamsFuture = frontendService.getCacheParams();
        try {
            byte[] cacheParamsSerialized = SerializationUtils.serialize(cacheParamsFuture.get());

            Future<Void> messageSentFuture = session.getRemote().sendBytesByFuture(ByteBuffer.wrap(cacheParamsSerialized));
            messageSentFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        System.out.printf("Server:: Got msg: %s%n", msg);
    }
}
