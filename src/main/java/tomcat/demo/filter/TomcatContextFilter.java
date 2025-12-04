package tomcat.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
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
            // 挂载到上下文
//            request.setAttribute("TomcatContext", holder);
            chain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("[TomcatContextFilter] error: " + e.getMessage());
        }
    }
}
