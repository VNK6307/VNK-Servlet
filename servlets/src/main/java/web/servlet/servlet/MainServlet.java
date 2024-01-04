package web.servlet.servlet;

import web.servlet.controller.PostController;
import web.servlet.repository.PostRepository;
import web.servlet.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() {
        final PostRepository repository = new PostRepository();
        final PostService service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final String path = req.getRequestURI();
            final var method = req.getMethod();

            // primitive routing
            if (method.equals("GET") && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }
            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
                long id = parseID(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals("POST") && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
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
        Pattern pattern = Pattern.compile("/api/posts/(\\d+)");
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