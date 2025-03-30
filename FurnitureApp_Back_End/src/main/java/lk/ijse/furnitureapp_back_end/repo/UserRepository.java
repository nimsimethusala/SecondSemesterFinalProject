package lk.ijse.furnitureapp_back_end.repo;

import lk.ijse.furnitureapp_back_end.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,String> {

    User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);

}