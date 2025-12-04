package tomcat.demo.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 1、ServletContextListener（最常用）：对于每个WebApp，Web服务器都会为其创建一个全局唯一的ServletContext实例
 * 2、HttpSessionListener：监听HttpSession的创建和销毁事件；
 * 3、ServletRequestListener：监听ServletRequest请求的创建和销毁事件；
 * 4、ServletRequestAttributeListener：监听ServletRequest请求的属性变化事件（即调用ServletRequest.setAttribute()方法）；
 * 5、ServletContextAttributeListener：监听ServletContext的属性变化事件（即调用ServletContext.setAttribute()方法）；
 */
@WebListener
public class TomcatAppListener implements ServletContextListener {
    // // 在此初始化WebApp,例如打开数据库连接池等
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[TomcatAppListener] AppListener.contextInitialized = " + sce.getServletContext());
    }

    // 在此清理WebApp,例如关闭数据库连接池等
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[TomcatAppListener] WebApp destroyed.");
    }
}
