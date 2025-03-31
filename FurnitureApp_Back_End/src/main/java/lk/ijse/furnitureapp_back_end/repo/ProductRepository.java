package lk.ijse.furnitureapp_back_end.repo;

import lk.ijse.furnitureapp_back_end.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByName(String name);
}
