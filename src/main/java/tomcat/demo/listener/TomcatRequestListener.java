package tomcat.demo.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import tomcat.demo.context.TomcatRequestContextHolder;

@WebListener
public class TomcatRequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sce) {
        System.out.println("[TomcatRequestListener] Request initialized for URI");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sce) {
        System.out.println("[TomcatRequestListener] Request destroyed for URI");
        System.out.println("[TomcatRequestListener] TomcatRequestContext is " + TomcatRequestContextHolder.get());

    }
}
