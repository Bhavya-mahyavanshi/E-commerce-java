package src.code.model;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProductById(int id) {
        return products.get(id);
    }

    public Map<Integer, Product> getAllProducts() {
        return products;
    }
}
