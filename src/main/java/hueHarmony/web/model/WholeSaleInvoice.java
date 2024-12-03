package hueHarmony.web.model;

import hueHarmony.web.model.enums.PaymentMethod;
import hueHarmony.web.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name="whole_sale_invoice")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WholeSaleInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false, unique = true)
    private Long invoiceId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id", nullable = false)
    private WholeSaleOrder wholeSaleOrder;

    @Temporal(TemporalType.DATE)
    @Column(name = "invoice_date", nullable = false)
    private Date invoiceDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "net_amount", nullable = false)
    private Double netAmount;

    @Column(name = "billing_address", length = 500)
    private String billingAddress;

    @Column(name = "payment_method", length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "notes", length = 1000)
    private String notes;

    @OneToMany(mappedBy = "wholeSaleInvoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DebitNote> debitNotes ;

    @OneToMany(mappedBy = "wholeSaleInvoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditNote> creditNotes;
}

