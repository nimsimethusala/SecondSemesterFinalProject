package lk.ijse.furnitureapp_back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    private String name;
    private String description;
    private double price;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Constructor excluding productId since it will be auto-generated
    public Product(String name, Category category, double price, String description, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
