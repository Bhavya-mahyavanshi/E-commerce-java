package src.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import src.code.model.Product;
import src.code.model.User;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File productsFile = new File("data/products.json");
    private final File usersFile = new File("data/users.json");

    public Map<Integer, Product> loadProducts(){
        try{
            if(!productsFile.exists()) return new HashMap<>();
            return mapper.readValue(productsFile, new TypeReference<Map<Integer, Product>>() {});
        }catch(Exception e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public void saveProducts(Map<Integer, Product> products){
        try {
            productsFile.getParentFile().mkdirs();
            mapper.writerWithDefaultPrettyPrinter().writeValue(productsFile, products);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Map<String, User> loadUsers(){
        try {
            if(!usersFile.exists()) return new HashMap<>();
            return mapper.readValue(usersFile, new TypeReference<Map<String,User>>() {});
        } catch (Exception e) { e.printStackTrace(); return new HashMap<>(); }
    }

    public void saveUsers(Map<String, User> users){
        try {
            usersFile.getParentFile().mkdirs();
            mapper.writerWithDefaultPrettyPrinter().writeValue(usersFile, users);
        } catch (Exception e) { e.printStackTrace(); }
    }
}

