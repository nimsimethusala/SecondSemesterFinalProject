package lk.ijse.furnitureapp_back_end.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @ManyToMany
    @JoinTable(name = "product_material", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Material> materials;

    // Constructor excluding productId since it will be auto-generated
    public Product(String name, Category category, double price, String description, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
