package giuaki.application.service;

import giuaki.application.entity.Product;
import giuaki.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }
    @Override
    public void deleteProduct(Long id) {}
    @Override
    public Product save(Product product) {return null;}
    @Override
    public Iterable<Product> getSneakerByName(String name) {
        return productRepository.findProductsByNameContainingIgnoreCase(name);
    }
    @Override
    public Iterable<Product> filterSneaker(Map<String,String> data) {
        String name = "";
        Integer price_min = 0, price_max = 1000000000;
        String brand = "", gender = "";
        String sort = "desc";
        if(data.get("sort") != null) {
            sort = data.get("sort");
        }
        if(data.get("price_min") != null) {
            price_min = Integer.parseInt(data.get("price_min"));
        }
        if(data.get("price_max") != null) {
            price_max = Integer.parseInt(data.get("price_max"));
        }
        if(data.get("brand") != null) {
            brand = data.get("brand");
        }
        if(data.get("gender") != null) {
            gender = data.get("gender");
        }
        if (data.get("name") != null) {
            name = data.get("name");
        }
        if(sort.equals("desc")) {
            if(gender.equals("") || gender.equals(null))
            {
                return productRepository.findProductsByPriceBetweenAndBrandContainingAndNameContainingOrderByPriceDesc(price_min, price_max, brand, name);
            }
            return productRepository.findProductsByPriceBetweenAndBrandContainingAndGenderAndNameContainingOrderByPriceDesc(price_min, price_max, brand, gender, name);
        }
        else {
            if(gender.equals("") || gender.equals(null))
            {
                return productRepository.findProductsByPriceBetweenAndBrandContainingAndNameContainingOrderByPriceAsc(price_min, price_max, brand, name);
            }
            return productRepository.findProductsByPriceBetweenAndBrandContainingAndGenderAndNameContainingOrderByPriceAsc(price_min, price_max, brand, gender, name);
        }
    }
}
