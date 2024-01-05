package web.servlet.servlet;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import web.servlet.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainServlet extends HttpServlet {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "applicationContext.xml"
    );
    private final PostController controller = context.getBean("controller", PostController.class);
    private final static String GET = "GET";
    private final static String POST = "POST";
    private final static String DELETE = "DELETE";
    private final static String targetPath = "/api/posts";

    @Override
    public void init() {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final String path = req.getRequestURI();
            final var method = req.getMethod();

            // primitive routing
            if (method.equals(GET) && path.equals(targetPath)) {
                controller.all(resp);
                return;
            }
//            if (method.equals(GET) && path.matches("/api/posts/\\d+")) {
            if (method.equals(GET) && path.matches(targetPath + "/\\d+")) {
                long id = parseID(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST) && path.equals(targetPath)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE) && path.matches(targetPath + "/\\d+")) {
                long idDel = parseID(path);
                controller.removeById(idDel, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long parseID(String path) {
        long id;
        Pattern pattern = Pattern.compile(targetPath + "/(\\d+)");
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches()) {
            String idStr = matcher.group(1);
            id = Long.parseLong(idStr);
        } else {
            throw new IllegalArgumentException("Invalid path format");
        }
        return id;
    }
}