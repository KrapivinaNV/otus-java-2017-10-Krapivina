package otus.service.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;
import otus.messageSystem.MessageSystem;
import otus.service.frontend.FrontendService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "My WebSocket Servlet", urlPatterns = {"/my-web-socket-servlet"})
public class MyWebSocketServlet extends WebSocketServlet {

    private FrontendService frontendService;

    @Override
    public void init(ServletConfig config) throws ServletException {

        ApplicationContext applicationContext = (ApplicationContext) config.getServletContext()
                .getAttribute("applicationContext");

        frontendService = applicationContext.getBean(FrontendService.class);

        MessageSystem messageSystem = applicationContext.getBean(MessageSystem.class);
        messageSystem.start();

        super.init(config);
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(5000L);
        factory.setCreator(new MyWebSocketCreator(frontendService));
    }
}