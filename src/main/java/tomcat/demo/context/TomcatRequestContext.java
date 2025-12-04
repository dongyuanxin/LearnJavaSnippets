package tomcat.demo.context;

import lombok.Data;

@Data
public class TomcatRequestContext {
    private String requestId;

    @Override
    public String toString() {
        return "TomcatRequestContext{" +
                "requestId='" + requestId + '\'' +
                '}';
    }
}
