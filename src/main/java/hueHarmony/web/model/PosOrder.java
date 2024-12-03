package hueHarmony.web.model;

import hueHarmony.web.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pos_order")
public class PosOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Integer phoneNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PosOrderItem> items;

    @Column(nullable = false)
    private float total;

    @Column(nullable = false)
    private float subTotal;

    @Column(nullable = false)
    private float discount;

    @Column(nullable = false)
    private String paymentMethod; // e.g., "Cash", "Card"

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)  // Store enum as a String in the database
    private OrderStatus orderStatus = OrderStatus.COMPLETED;// e.g., "Pending", "Completed"

    @Column(nullable = false)
    private Long cashierId;

}
