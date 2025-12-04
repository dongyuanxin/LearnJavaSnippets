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
 * 作用：承接所有的静态资源、jsp文件，匹配所有 /views/* 路由
 * 设计考虑：
 * - 如果直接在 webapp/ 下放置 jsp 文件，可以直接访问到JSP文件，这样做会导致文件不受保护
 * - 更安全推荐做法是：
 *      1、静态资源/jsp 放到特定目录下 WEB-INF（无法直接访问）
 *      2、再利用mvc分离的思想，创建一个 ViewsServlet 来处理静态资源，负责转发
 */
@WebServlet(urlPatterns = "/views/*")
public class ViewsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 获取路径信息，例如：/views/ip → pathInfo = "/ip"
        String pathInfo = req.getPathInfo();

        System.out.println("pathInfo: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 例如 pathInfo = "/ip" → 转发到 /WEB-INF/views/ip.jsp
        String jspPath = "/WEB-INF/views" + pathInfo + ".jsp";

        // 使用RequestDispatcher进行转发
        req.getRequestDispatcher(jspPath).forward(req, resp);
    }
}
