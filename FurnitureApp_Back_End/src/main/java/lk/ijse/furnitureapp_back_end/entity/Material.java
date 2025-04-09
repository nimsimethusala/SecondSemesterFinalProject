/*
package lk.ijse.furnitureapp_back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "material")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID materialId;

    private String name;

    @ManyToMany(mappedBy = "materials")
    private List<Product> products;
}
*/
