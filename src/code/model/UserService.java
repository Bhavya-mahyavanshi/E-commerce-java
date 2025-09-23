package src.code.model;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<Integer, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUserById(int id) {
        return users.get(id);
    }
}
