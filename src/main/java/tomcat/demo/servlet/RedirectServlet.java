package tomcat.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet(urlPatterns = "/hi")
public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String redirectUrl = "/" + (name == null ? "" : "?name=" + name);
        // 302 临时重定向
        resp.sendRedirect(redirectUrl);

//        301 永久重定向
//        resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY); // 301
//        resp.setHeader("Location", "/");

//        两者区别：两者的区别是，如果服务器发送301永久重定向响应，浏览器会缓存 /hi 到/ 这个重定向的关联，下次请求 /hi 的时候，浏览器就直接发送 / 请求了。
    }
}
