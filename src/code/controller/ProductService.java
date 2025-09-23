package src.code.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import src.code.model.Product;

public class ProductService {
    private final Map<Integer, Product> products;
    private final DataStore ds;

    public ProductService(DataStore ds) {
        this.ds = ds;
        Map<Integer, Product> loaded = ds.loadProducts();
        this.products = new ConcurrentHashMap<>(loaded);
    }

    public Map<Integer, Product> getAll() { return products; }

    public Product findById(int id){ return products.get(id); }

    public void addProduct(Product p){
        products.put(p.getId(), p);
        ds.saveProducts(products);
    }

    public void updateStock(int productId, int newStock){
        Product p = products.get(productId);
        if(p != null){
            p.setStock(newStock);
            ds.saveProducts(products);
        }
    }

    public int nextProductId(){
        return products.keySet().stream().mapToInt(i->i).max().orElse(0) + 1;
    }
}