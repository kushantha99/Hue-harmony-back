package hueHarmony.web.service;

import hueHarmony.web.dto.NoteDto;
import hueHarmony.web.model.CreditNote;
import hueHarmony.web.model.DebitNote;
import hueHarmony.web.model.WholeSaleInvoice;
import hueHarmony.web.repository.CreditNoteRepository;
import hueHarmony.web.repository.DebitNoteRepository;
import hueHarmony.web.repository.InvoiceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {

    @PersistenceContext
    private EntityManager entityManager;

    private final DebitNoteRepository debitNoteRepository;

    private final CreditNoteRepository creditNoteRepository;

    private final InvoiceRepository invoiceRepository;




    public void createDebitNote(NoteDto noteDto) throws Exception {

        Optional<WholeSaleInvoice> wholeSaleInvoice =invoiceRepository.findById(noteDto.getInvoiceId());

        DebitNote debitNote = DebitNote.builder()
                .amount(noteDto.getAmount())
                .reason(noteDto.getReason())
                .issuedDate(new Date())
                .wholeSaleInvoice(entityManager.getReference(WholeSaleInvoice.class,noteDto.getInvoiceId()))
                .build();

        if(wholeSaleInvoice.isEmpty()) throw new Exception("Invoice not available");

        if(wholeSaleInvoice.get().getDebitNotes()==null){
            wholeSaleInvoice.get().setDebitNotes(List.of(debitNote));
        }else{
            wholeSaleInvoice.get().getDebitNotes().add(debitNote);
        }

        debitNoteRepository.save(debitNote);
    }

    public void createCreditNote(NoteDto noteDto) throws Exception {

        Optional<WholeSaleInvoice> wholeSaleInvoice =invoiceRepository.findById(noteDto.getInvoiceId());

        CreditNote creditNote = CreditNote.builder()
                .amount(noteDto.getAmount())
                .reason(noteDto.getReason())
                .issuedDate(new Date())
                .wholeSaleInvoice(entityManager.getReference(WholeSaleInvoice.class,noteDto.getInvoiceId()))
                .build();

        if(wholeSaleInvoice.isEmpty()) throw new Exception("Invoice not available");

       if(wholeSaleInvoice.get().getCreditNotes()==null){
           wholeSaleInvoice.get().setCreditNotes(List.of(creditNote));
       }else{
           wholeSaleInvoice.get().getCreditNotes().add(creditNote);
       }


        creditNoteRepository.save(creditNote);
    }

    public List<DebitNote> getDebitNotesByInvoice(Long invoiceId) throws Exception {
        Optional<WholeSaleInvoice> wholeSaleInvoice = invoiceRepository.findById(invoiceId);

        if(wholeSaleInvoice.isEmpty()) throw new Exception("Invoice not available");

        return wholeSaleInvoice.get().getDebitNotes();
    }

    public List<CreditNote> getCreditNotesByInvoice(Long invoiceId) throws Exception {
        Optional<WholeSaleInvoice> wholeSaleInvoice = invoiceRepository.findById(invoiceId);

        if(wholeSaleInvoice.isEmpty()) throw new Exception("Invoice not available");

        return wholeSaleInvoice.get().getCreditNotes();
    }
}
