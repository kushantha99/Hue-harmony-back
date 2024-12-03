package hueHarmony.web.model;

import hueHarmony.web.model.enums.LoyaltyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="loyalty_points")
public class Loyalty {

    @Id
    @Column(name = "contact_no")
    private Integer contactNo;

    @Column(name="loyalty_points")
    private Integer loyaltyPoints=0;

    @Enumerated(EnumType.STRING)
    private LoyaltyStatus loyaltyStatus=LoyaltyStatus.ACTIVE;

}
