package tomcat.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tomcat.demo.context.TomcatRequestContextHolder;

/**
 * 并发问题：一个Servlet类在服务器中只有一个实例，但对于每个HTTP请求，Web服务器会使用多线程执行请求。因此，这里的doGet和doPost是多线程并发的，如果类里定义了字段，要特别关注多线程并发的问题。
 */
@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    private static final Set<String> LANGUAGES = Set.of("en", "zh");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 和自己写 http 服务器相比，使用 Servlet 开发能屏蔽掉http协议，只关注业务逻辑。
        // 有了HttpServletRequest和HttpServletResponse这两个高级接口，我们就不需要直接处理HTTP协议。注意到具体的实现类是由各服务器提供的，而我们编写的Web应用程序只关心接口方法，并不需要关心具体实现的子类。
        resp.setContentType("text/html");
        String name = req.getParameter("name");
        if (name == null) {
            name = "world";
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAllCookies(req);

        printContext(req);

        String lang = req.getParameter("lang");
        if (lang != null && LANGUAGES.contains(lang)) {
            Cookie cookie = new Cookie("lang", lang);
            cookie.setPath("/"); // cookie 生效路径
            cookie.setMaxAge(8640000); // cookie 生效时间 8640000秒=100天
            resp.addCookie(cookie); // 添加响应
        }
        // getOutputStream 获取字节写入流；getWriter 获取字符写入流
        PrintWriter pw = resp.getWriter();
        // 写入响应前，无需设置setContentLength()：
        // 因为底层服务器会根据写入的字节数自动设置，如果写入的数据量很小，实际上会先写入缓冲区，如果写入的数据量很大，服务器会自动采用Chunked编码让浏览器能识别数据结束符而不需要设置Content-Length头
        pw.write("<h1>Hello, " + name + "</h1>");
        // 写入完必须调用flush，因为多数都会复用tcp连接。flush() 确保缓冲区中的数据立即发送给客户端。
        pw.flush();
        // 写入完毕后千万不要调用close()，原因同样是因为会复用TCP连接，如果关闭写入流，将关闭TCP连接，使得Web服务器无法复用此TCP连接，无法完成后续操作
    }

    private void printAllCookies(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("[HelloServlet] " + cookie.getName() + ": " + cookie.getValue());
            }
        }
    }

    private void printContext(HttpServletRequest req) {
        if (TomcatRequestContextHolder.get() == null) {
            System.out.println("[HelloServlet] TomcatRequestContext is null");
        } else {
            System.out.println("[HelloServlet] TomcatRequestContext is not null, it's " + TomcatRequestContextHolder.get());
        }
    }
}
