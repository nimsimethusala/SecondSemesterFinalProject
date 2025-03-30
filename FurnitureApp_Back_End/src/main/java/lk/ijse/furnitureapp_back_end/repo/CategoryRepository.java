package lk.ijse.furnitureapp_back_end.repo;

import lk.ijse.furnitureapp_back_end.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, String> {
    boolean existsByName(String name);

    List<Category> findByName(String name);
}
