package otus.jetty;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;
import otus.cache.CacheEngine;
import otus.common.DBService;
import otus.data.AddressDataSet;
import otus.data.DataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private CacheEngine<Long, DataSet> cacheEngine;
    private DBService dbService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext applicationContext = (ApplicationContext) config.getServletContext()
                .getAttribute("applicationContext");
        cacheEngine = applicationContext.getBean(CacheEngine.class);
        dbService = applicationContext.getBean(DBService.class);

        try {
            generateUsers(10, dbService);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cacheEngine.dispose();
        }
    }

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

    private static void generateUsers(int countUsers, DBService dbServiceHibernate) throws SQLException {
        for (int i = 0; i < countUsers; i++) {
            PhoneDataSet phone1 = new PhoneDataSet("11111111111");
            PhoneDataSet phone2 = new PhoneDataSet("22222222222");
            UserDataSet user = new UserDataSet(
                    "User" + i,
                    i,
                    new AddressDataSet("street"),
                    Sets.newHashSet(phone1, phone2)
            );
            phone1.setUser(user);
            phone2.setUser(user);
            dbServiceHibernate.save(user);
        }

        for (int i = 0; i < countUsers; i++) {
            Random random = new Random();
            int index = random.nextInt(countUsers) + 1;
            UserDataSet load = dbServiceHibernate.load(index, UserDataSet.class);
            System.out.println("id = " + index + " Data = " + load);
        }
    }
}