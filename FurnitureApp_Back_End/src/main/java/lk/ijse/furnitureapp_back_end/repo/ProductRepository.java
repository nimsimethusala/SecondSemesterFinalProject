package lk.ijse.furnitureapp_back_end.repo;

import lk.ijse.furnitureapp_back_end.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM product WHERE category_id = :categoryId ORDER BY product_id DESC LIMIT 3", nativeQuery = true)
    List<Product> findLastThreeByCategory(@Param("categoryId") UUID categoryId);

    List<Product> findByCategory_NameIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.category.name) = LOWER(:categoryName)")
    List<Product> findByCategoryNameIgnoreCase(String categoryName);

}
