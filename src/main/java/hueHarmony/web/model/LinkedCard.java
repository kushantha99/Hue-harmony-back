package hueHarmony.web.model;

import hueHarmony.web.model.enums.CardType;
import hueHarmony.web.model.enums.LinkedCardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "linked_card")
@Builder
public class LinkedCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int linkedCardId;

    @Column(name = "token", columnDefinition = "TEXT", unique = true)
    private String token;

    @Column(name = "gateway_customer_id", columnDefinition = "TEXT")
    private String gatewayCustomerId;

    @Column(name = "card_offset", columnDefinition = "VARCHAR", length = 4, nullable = false)
    private String cardOffset;

    @Column(name = "expire_date", columnDefinition = "VARCHAR", length = 5, nullable = false)
    private String expireDate;

    //create a trigger to make default when there is no active cards saved for customer
    @Column(name = "is_default", columnDefinition = "BOOLEAN")
    private boolean isDefault;

    @Column(name = "linked_card_type", columnDefinition = "VARCHAR", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType linkedCardType;

    @Column(name = "linked_card_status", columnDefinition = "VARCHAR", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private LinkedCardStatus linkedCardStatus = LinkedCardStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "retail_customer_id", nullable = false)
    private RetailCustomer retailCustomer;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
}
