package giuaki.application.repository;

import giuaki.application.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    Iterable<Product> findProductsByNameContainingIgnoreCase(String name);
    Iterable<Product> findProductsByPriceBetweenAndBrandContainingAndGenderAndNameContainingOrderByPriceAsc(Integer price_min, Integer price_max, String brand, String gender, String name);
    Iterable<Product> findProductsByPriceBetweenAndBrandContainingAndGenderAndNameContainingOrderByPriceDesc(Integer price_min, Integer price_max, String brand, String gender, String name);
    Iterable<Product> findProductsByPriceBetweenAndBrandContainingAndNameContainingOrderByPriceAsc(Integer price_min, Integer price_max, String brand, String name);
    Iterable<Product> findProductsByPriceBetweenAndBrandContainingAndNameContainingOrderByPriceDesc(Integer price_min, Integer price_max, String brand, String name);

    Product findSneakerById(long sneakerId);
}
