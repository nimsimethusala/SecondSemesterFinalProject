package lk.ijse.furnitureapp_back_end.dto;

import jakarta.persistence.*;
import lk.ijse.furnitureapp_back_end.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    private UUID productId;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private Category category;
}
