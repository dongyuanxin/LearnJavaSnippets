package tomcat.demo;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.catalina.connector.Connector;

import java.io.File;

public class TomcatServer {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        // tomcat默认是多线程模型（同步阻塞）
        // tomcat.setPort(Integer.getInteger("port", 8080));
        // tomcat.getConnector();

        // 获取并配置NIO连接器以启用Reactor模式
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(Integer.getInteger("port", 8080));
        tomcat.setConnector(connector);

        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
