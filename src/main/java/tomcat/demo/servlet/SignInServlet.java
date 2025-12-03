package tomcat.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Session 的意义: http 协议是无状态的，借助session（本质是cookies），就能识别客户端身份
 * Session 的本质：name为JESESSIONID的cookies，保存在浏览器；保存在服务器的session对象中
 * Session 的写入：req.getSession().setAttribute("user", name);
 * Session 的获取：req.getSession().getAttribute("user"); 参考 HelloSerlet 里的校验逻辑
 */
@WebServlet(urlPatterns = "/signin")
public class SignInServlet extends HttpServlet {
    private ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        users.put("bob", "bob123");
        users.put("alice", "alice123");
        users.put("tom", "tomcat");
    }


    // GET请求时显示登录页:
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        pw.write("<h1>Sign In</h1>");
        pw.write("<form action=\"/signin\" method=\"post\">");
        pw.write("<p>Username: <input name=\"username\"></p>");
        pw.write("<p>Password: <input name=\"password\" type=\"password\"></p>");
        pw.write("<p><button type=\"submit\">Sign In</button> <a href=\"/\">Cancel</a></p>");
        pw.write("</form>");
        pw.flush();
    }

    // POST请求时处理用户登录:
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String expectedPassword = users.get(name.toLowerCase());
        if (expectedPassword != null && expectedPassword.equals(password)) {
            // 登录成功:
            req.getSession().setAttribute("user", name);
            resp.sendRedirect("/");
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
