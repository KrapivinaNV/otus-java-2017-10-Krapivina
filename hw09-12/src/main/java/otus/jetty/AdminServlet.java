package otus.jetty;

import otus.cache.CacheHolder;
import otus.cache.MyCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final MyCache MY_CACHE = CacheHolder.getMyCache();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getCookies() != null && Arrays.stream(request.getCookies())
                .anyMatch(cookie -> cookie.getName().equals("authenticated") && cookie.getValue().equals("true"))) {

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("HitCount", MY_CACHE.getHitCount());
            pageVariables.put("MissCount", MY_CACHE.getMissCount());
            pageVariables.put("GCMissCount", MY_CACHE.getGCMissCount());

            String page = getPage(pageVariables, ADMIN_PAGE_TEMPLATE);
            response.getWriter().println(page);
            setOK(response);

        } else {
            response.sendRedirect("/login");
        }
    }

    private static String getPage(Map<String, Object> pageVariables, String page_tml) throws IOException {
        return TemplateProcessor.instance().getPage(page_tml, pageVariables);
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}