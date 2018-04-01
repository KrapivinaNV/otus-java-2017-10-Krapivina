package otus.service.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import otus.service.frontend.FrontendService;

public class MyWebSocketCreator implements WebSocketCreator {

    private final FrontendService frontendService;

    MyWebSocketCreator(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return new MyServerWebSocket(frontendService);
    }
}
