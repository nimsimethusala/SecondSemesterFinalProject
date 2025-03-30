package lk.ijse.furnitureapp_back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String paymentMethod;
    private double amount;
}
