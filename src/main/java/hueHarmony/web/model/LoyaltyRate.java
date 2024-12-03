package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="loyalty_rate")
public class LoyaltyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loyaltyRateId;

    @Column()
    private float loyaltyPoint;

    @Column()
    private float priceRangeStart;

    @Column()
    private float priceRangeEnd;

}
