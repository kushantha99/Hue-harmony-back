package hueHarmony.web.controller;

import hueHarmony.web.dto.NoteDto;
import hueHarmony.web.model.CreditNote;
import hueHarmony.web.model.DebitNote;
import hueHarmony.web.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {


    private final NoteService noteService;

    // Create Debit Note
    @PostMapping("/debit")
    public ResponseEntity<?> createDebitNote(@RequestBody NoteDto debitNoteDto) throws Exception {
        noteService.createDebitNote(debitNoteDto);
        return ResponseEntity.ok("Debit note created");
    }

    // Create Credit Note
    @PostMapping("/credit")
    public ResponseEntity<?> createCreditNote(@RequestBody NoteDto creditNoteDto) throws Exception {
        noteService.createCreditNote(creditNoteDto);
        return ResponseEntity.ok("Credit note created");
    }

    // Get Debit Notes by Invoice ID
    @GetMapping("/debit/{invoiceId}")
    public ResponseEntity<?> getDebitNotesByInvoice(@PathVariable Long invoiceId) throws Exception {
        List<DebitNote> debitNotes = noteService.getDebitNotesByInvoice(invoiceId);
        return ResponseEntity.ok(debitNotes);
    }

    // Get Credit Notes by Invoice ID
    @GetMapping("/credit/{invoiceId}")
    public ResponseEntity<?> getCreditNotesByInvoice(@PathVariable Long invoiceId) throws Exception {
        List<CreditNote> creditNotes = noteService.getCreditNotesByInvoice(invoiceId);
        return ResponseEntity.ok(creditNotes);
    }
}

