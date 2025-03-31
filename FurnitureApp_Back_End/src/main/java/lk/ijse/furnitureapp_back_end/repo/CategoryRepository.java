package lk.ijse.furnitureapp_back_end.repo;

import lk.ijse.furnitureapp_back_end.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository  extends JpaRepository<Category, UUID> {
    boolean existsByName(String name);

    List<Category> findByName(String name);

}
