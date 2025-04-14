package lk.ijse.furnitureapp_back_end.repo;

import lk.ijse.furnitureapp_back_end.entity.Cart;
import lk.ijse.furnitureapp_back_end.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser_Uid(UUID uid);

    void deleteByUser(User user);
}
