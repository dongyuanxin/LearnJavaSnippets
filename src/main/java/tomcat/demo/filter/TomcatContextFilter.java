package tomcat.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import tomcat.demo.context.TomcatRequestContext;
import tomcat.demo.context.TomcatRequestContextHolder;

import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class TomcatContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 初始化context
        TomcatRequestContext context = new TomcatRequestContext();
        context.setRequestId(UUID.randomUUID().toString());

        // 绑定到 ThreadLocal
        try (var holder = new TomcatRequestContextHolder(context)) {
            // 修改返回头。如果在设置头部之前已经有内容写入响应，响应可能已被提交；一旦响应提交，后续的头部设置将无效（很好理解，是stream写入的，需要按顺序来）
            // 这个例子还比较简单，复杂的可以看这篇文章，通过 HttpServletResponseWrapper 劫持响应，对返回整体都进行修改（原返回会导致socket被写入）
            // 复杂用例：https://liaoxuefeng.com/books/java/web/filter/response/index.html
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("X-Request-Id", context.getRequestId());
            httpResponse.setHeader("X-Powered-By", "JavaEE Servlet");

            // 挂载到上下文（不需要了，看 TomcatRequestContextHolder 实现）
//            request.setAttribute("TomcatContext", holder);
            chain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("[TomcatContextFilter] error: " + e.getMessage());
        }
    }
}
