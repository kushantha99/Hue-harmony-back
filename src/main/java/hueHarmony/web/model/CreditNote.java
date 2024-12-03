package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="credit_note")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_note_id")
    private Long creditNoteId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "wholesale_invoice_id", nullable = false)
    private WholeSaleInvoice wholeSaleInvoice;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "reason", length = 255)
    private String reason;

    @Column(name = "issued_date")
    @Temporal(TemporalType.DATE)
    private Date issuedDate;
}
