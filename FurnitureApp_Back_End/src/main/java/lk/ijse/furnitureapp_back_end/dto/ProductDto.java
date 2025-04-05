package lk.ijse.furnitureapp_back_end.dto;

import lk.ijse.furnitureapp_back_end.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    private String categoryName;
    private Category category;
    MultipartFile[] imageFiles;

    public ProductDto(UUID productId, String name, String categoryName, double price, String description, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }
}
