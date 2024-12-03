package hueHarmony.web.model;

import hueHarmony.web.model.WholeSaleInvoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="debit_note")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebitNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debit_note_id")
    private Long debitNoteId;

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