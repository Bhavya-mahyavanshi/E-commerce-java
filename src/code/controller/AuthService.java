package src.code.controller;

import org.mindrot.jbcrypt.BCrypt;

import src.code.model.Role;
import src.code.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

public class AuthService {
    private Map<String, User> users = new ConcurrentHashMap<>();
    private DataStore dataStore;

    public AuthService(DataStore ds){
        this.dataStore = ds;
        Map<String, User> loaded = ds.loadUsers();
        if(loaded != null) users.putAll(loaded);
    }

    public User register(String username, String plainPassword, Role role){
        if(users.containsKey(username)) return null;
        String hash = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        int id = users.size() + 1;
        User u = new User(id, username, hash, role);
        users.put(username, u);
        dataStore.saveUsers(users);

        return u;
    }

    public User login(String username, String plainPassword){
        User u = users.get(username);
        if(u == null) return null;
        if(BCrypt.checkpw(plainPassword, u.getPasswordHash())) return u;
        return null;
    }

    public void seedEmployeeIfNone(String defaultUsername, String defaultPassword){
        boolean hasEmployee = users.values().stream().anyMatch(u->u.getRole()==Role.EMPLOYEE);
        if(!hasEmployee){
            register(defaultUsername, defaultPassword, Role.EMPLOYEE);
        }
    }
}
