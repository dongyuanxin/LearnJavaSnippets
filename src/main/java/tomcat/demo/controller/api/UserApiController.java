package tomcat.demo.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import tomcat.demo.framework.GetMapping;

import java.util.HashMap;
import java.util.Map;

public class UserApiController {

    public static final Map<String, UserApiControllerPerson> PERSON_MAP = new HashMap<>();

    public UserApiController() {
        PERSON_MAP.put("tom", new UserApiControllerPerson("tom", 18));
        PERSON_MAP.put("bob", new UserApiControllerPerson("bob", 19));
        PERSON_MAP.put("alice", new UserApiControllerPerson("alice", 20));
    }

    @GetMapping(value = "/api/user")
    public UserApiControllerPerson index(HttpServletRequest req) {
        String name = req.getParameter("name");
        if (name == null) {
            return null;
        }

        var person = PERSON_MAP.get(name);
        if (person == null) {
            return null;
        }

        return person;
    }
}

@Data
class UserApiControllerPerson {
    private String name;
    private int age;

    public UserApiControllerPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
