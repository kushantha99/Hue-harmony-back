package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Loyalty_discount_rates")
public class LoyaltyDiscountRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int LoyaltyDiscountRatesId;

    @Column()
    private float discountRatePerLoyaltyPoint;
}
