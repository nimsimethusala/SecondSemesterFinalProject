package lk.ijse.furnitureapp_back_end.dto;

import jakarta.persistence.*;
import lk.ijse.furnitureapp_back_end.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private UUID categoryId;
    private String name;
    private String status;
}
