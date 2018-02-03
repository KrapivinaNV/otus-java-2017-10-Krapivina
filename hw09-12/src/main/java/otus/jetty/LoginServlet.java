package otus.jetty;

import com.google.common.collect.ImmutableMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String page = getPage(ImmutableMap.of("login", ""), LOGIN_PAGE_TEMPLATE);
        response.getWriter().println(page);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String requestLogin = request.getParameter("login");
        String requestPassword = request.getParameter("password");

        Map<String, Object> pageVariables = new HashMap<>();

        if (requestLogin != null
                && requestPassword != null
                && requestLogin.equals("sa")
                && requestPassword.equals("sa")) {
            setOkRedirect(response);
            response.addCookie(new Cookie("authenticated", "true"));
            response.sendRedirect("/admin");

        } else {
            response.addCookie(new Cookie("authenticated", "false"));
            pageVariables.put("login", requestLogin == null ? "" : requestLogin);
            String page = getPage(pageVariables, LOGIN_PAGE_TEMPLATE);
            response.getWriter().println(page);
            setUnauthorized(response);
        }
    }

    private void setOkRedirect(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FOUND);
    }

    private void setUnauthorized(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private static String getPage(Map<String, Object> pageVariables, String page_tml) throws IOException {
        return TemplateProcessor.instance().getPage(page_tml, pageVariables);
    }
}