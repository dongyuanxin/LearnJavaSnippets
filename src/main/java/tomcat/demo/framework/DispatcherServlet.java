package tomcat.demo.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import tomcat.demo.controller.api.UserApiController;

/**
 * DispatcherServlet 作用：
 * 0、承接所有的 /api/* 请求，在doGet和doPost中转发给 process 方法，然后 process 根据path匹配对应存储在getMappings或者postMappings中的 Dispatcher
 * 1、在init方法中扫描所有Controller，将 Controller 中使用了“注解”的方法注册为 Dispatcher(key为path，存储在getMappings或者postMappings中）
 * 2、在注册 Dispatcher 前，检查对应方法的返回类型、扫描并且保存方法的参数类型列表、参数名称列表
 *
 * Dispatcher 作用：接收到请求会从 process 转发下来
 * 1、负责参数解析和绑定（根据process传来的req/resp，外加前面保存的参数类型列表、参数名称列表）
 * 2、负责触发 Controller 的方法执行
 * 3、拿到方法执行结果，将结果以 json 格式写入 resp 中
 */

@WebServlet(urlPatterns = "/api/*")
public class DispatcherServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, GetDispatcher> getMappings = new HashMap<>();

    private Map<String, PostDispatcher> postMappings = new HashMap<>();

    // TODO: 可指定package并自动扫描:
    private List<Class<?>> controllers = List.of(UserApiController.class);

    /**
     * 1、当Servlet容器创建当前Servlet实例后，会自动调用init(ServletConfig)方法
     * 2、这里相当于一个IoC容器，将Controller中的方法注册为Dispatcher
     */
    @Override
    public void init() throws ServletException {
        logger.info("init {}...", getClass().getSimpleName());
        ObjectMapper objectMapper = new ObjectMapper();
        // FAIL_ON_UNKNOWN_PROPERTIES: 当 JSON 中包含目标 Java 类中不存在的属性时的行为
        // 默认为 true，但是改动后为false，遇到未知属性不会失败。
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 依次处理每个Controller:
        for (Class<?> controllerClass : controllers) {
            try {
                Object controllerInstance = controllerClass.getConstructor().newInstance();
                // 依次处理每个Method:
                for (Method method : controllerClass.getMethods()) {
                    if (method.getAnnotation(GetMapping.class) != null) {
                        // 处理@Get:
                        if (method.getReturnType() == void.class) {
                            throw new UnsupportedOperationException(
                                    "Unsupported return type: " + method.getReturnType() + " for method: " + method);
                        }
                        for (Class<?> parameterClass : method.getParameterTypes()) {
                            if (!supportedGetParameterTypes.contains(parameterClass)) {
                                throw new UnsupportedOperationException(
                                        "Unsupported parameter type: " + parameterClass + " for method: " + method);
                            }
                        }
                        String[] parameterNames = Arrays.stream(method.getParameters()).map(p -> p.getName())
                                .toArray(String[]::new);
                        String path = method.getAnnotation(GetMapping.class).value();
                        logger.info("Found GET: {} => {}", path, method);
                        this.getMappings.put(path, new GetDispatcher(controllerInstance, method, parameterNames,
                                method.getParameterTypes()));
                    } else if (method.getAnnotation(PostMapping.class) != null) {
                        // 处理@Post:
                        if (method.getReturnType() == void.class) {
                            throw new UnsupportedOperationException(
                                    "Unsupported return type: " + method.getReturnType() + " for method: " + method);
                        }
                        Class<?> requestBodyClass = null;
                        for (Class<?> parameterClass : method.getParameterTypes()) {
                            if (!supportedPostParameterTypes.contains(parameterClass)) {
                                if (requestBodyClass == null) {
                                    requestBodyClass = parameterClass;
                                } else {
                                    throw new UnsupportedOperationException("Unsupported duplicate request body type: "
                                            + parameterClass + " for method: " + method);
                                }
                            }
                        }
                        String path = method.getAnnotation(PostMapping.class).value();
                        logger.info("Found POST: {} => {}", path, method);
                        this.postMappings.put(path, new PostDispatcher(controllerInstance, method,
                                method.getParameterTypes(), objectMapper));
                    }
                }
            } catch (ReflectiveOperationException e) {
                throw new ServletException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp, this.getMappings);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp, this.postMappings);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp,
                         Map<String, ? extends AbstractDispatcher> dispatcherMap) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        AbstractDispatcher dispatcher = dispatcherMap.get(path);
        if (dispatcher == null) {
            resp.sendError(404);
            return;
        }
        try {
            dispatcher.invoke(req, resp);
        } catch (ReflectiveOperationException e) {
            throw new ServletException(e);
        }
    }

    private static final Set<Class<?>> supportedGetParameterTypes = Set.of(int.class, long.class, boolean.class,
            String.class, HttpServletRequest.class, HttpServletResponse.class, HttpSession.class);

    private static final Set<Class<?>> supportedPostParameterTypes = Set.of(HttpServletRequest.class,
            HttpServletResponse.class, HttpSession.class);
}

abstract class AbstractDispatcher {

    public abstract void invoke(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ReflectiveOperationException;

    protected void writeJsonResponse(HttpServletResponse response, Object result) throws IOException {
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        // writeValue方法：直接将Java对象序列化并写入到指定的目标（如OutputStream、Writer等）
        mapper.writeValue(writer, result == null ? null : result);
        writer.flush();
    }
}

class GetDispatcher extends AbstractDispatcher {

    final Object instance;
    final Method method;
    final String[] parameterNames;
    final Class<?>[] parameterClasses;

    public GetDispatcher(Object instance, Method method, String[] parameterNames, Class<?>[] parameterClasses) {
        super();
        this.instance = instance;
        this.method = method;
        this.parameterNames = parameterNames;
        this.parameterClasses = parameterClasses;
    }

    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ReflectiveOperationException {

        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            String parameterName = parameterNames[i];
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else if (parameterClass == int.class) {
                arguments[i] = Integer.valueOf(getOrDefault(request, parameterName, "0"));
            } else if (parameterClass == long.class) {
                arguments[i] = Long.valueOf(getOrDefault(request, parameterName, "0"));
            } else if (parameterClass == boolean.class) {
                arguments[i] = Boolean.valueOf(getOrDefault(request, parameterName, "false"));
            } else if (parameterClass == boolean.class) {
                arguments[i] = Boolean.valueOf(getOrDefault(request, parameterName, "false"));
            } else if (parameterClass == String.class) {
                arguments[i] = getOrDefault(request, parameterName, "");
            } else {
                throw new RuntimeException("Missing handler for type: " + parameterClass);
            }
        }

        Object result = this.method.invoke(this.instance, arguments);
        super.writeJsonResponse(response, result);
    }

    private String getOrDefault(HttpServletRequest request, String name, String defaultValue) {
        String s = request.getParameter(name);
        return s == null ? defaultValue : s;
    }
}

class PostDispatcher extends AbstractDispatcher {

    final Object instance;
    final Method method;
    final Class<?>[] parameterClasses;
    final ObjectMapper objectMapper;

    public PostDispatcher(Object instance, Method method, Class<?>[] parameterClasses, ObjectMapper objectMapper) {
        this.instance = instance;
        this.method = method;
        this.parameterClasses = parameterClasses;
        this.objectMapper = objectMapper;
    }

    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ReflectiveOperationException {
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else {
                BufferedReader reader = request.getReader();
                arguments[i] = this.objectMapper.readValue(reader, parameterClass);
            }
        }

        Object result = this.method.invoke(this.instance, arguments);
        super.writeJsonResponse(response, result);
    }
}
