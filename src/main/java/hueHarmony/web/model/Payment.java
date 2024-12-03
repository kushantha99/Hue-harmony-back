package hueHarmony.web.model;

import hueHarmony.web.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_no", nullable = false, unique = true)
    @Builder.Default
    private String paymentNo = generateUniquePaymentNumber();

    @Column(name = "payment_amount", nullable = false, columnDefinition = "REAL DEFAULT 0 CHECK(payment_amount > 0)")
    private float paymentAmount;

    @Column(name = "payment_description", nullable = false, columnDefinition = "TEXT", length = 500)
    private String paymentDescription;

    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", columnDefinition = "VARCHAR", length = 10, nullable = false)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Transient
    private static final Random random = new Random();

    @Transient
    private static String generateUniquePaymentNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = dateFormat.format(new Date());
        int randomNum = random.nextInt(10000);
        return timestamp + String.format("%04d", randomNum);
    }
}
