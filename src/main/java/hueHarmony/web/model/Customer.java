package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer")
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(name = "first_name", columnDefinition = "VARCHAR", length = 60, nullable = false)
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR", length = 60, nullable = false)
    private String lastName;

    @ElementCollection
    @CollectionTable(name = "customer_contact_numbers", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "contact_number", columnDefinition = "VARCHAR", length = 10, nullable = false)
    private Set<String> contactNos;

    @Column(name = "email", columnDefinition = "VARCHAR", length = 200, nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RetailCustomer retailCustomer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WholeSaleCustomer wholeSaleCustomer;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.MERGE)
//    private List<Order> orders;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
//    private List<LinkedCard> linkedCards;

}
