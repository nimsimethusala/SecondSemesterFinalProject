package lk.ijse.furnitureapp_back_end.dto;

import jakarta.persistence.*;
import lk.ijse.furnitureapp_back_end.entity.Product;
import lk.ijse.furnitureapp_back_end.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {
    private UUID reviewId;
    private User user;
    private Product product;
    private String comment;
    private LocalDateTime reviewDate;
}
