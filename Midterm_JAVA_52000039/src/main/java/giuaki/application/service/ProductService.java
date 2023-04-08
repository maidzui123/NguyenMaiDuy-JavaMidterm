package giuaki.application.service;

import giuaki.application.entity.Product;

import java.util.Map;
import java.util.Optional;

public interface ProductService {
    public Iterable<Product> getAllProducts();
    public Product getProduct(Long id);
    public void deleteProduct(Long id);
    public Product save(Product product);
    public Iterable<Product> getSneakerByName(String name);
    public Iterable<Product> filterSneaker(Map<String,String> data);
}
