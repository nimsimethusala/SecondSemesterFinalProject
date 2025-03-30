package lk.ijse.furnitureapp_back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String comment;
    private LocalDateTime reviewDate;
}
