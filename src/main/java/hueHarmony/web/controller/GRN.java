package hueHarmony.web.controller;

import hueHarmony.web.dto.GoodsReceivedNoteDto;
import hueHarmony.web.model.GoodsReceivedNote;
import hueHarmony.web.service.GRNService;
import hueHarmony.web.util.ErrorFormat;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grn")
@RequiredArgsConstructor
public class GRN {

    private final ErrorFormat errorFormat;
    @Autowired
    private GRNService grnService;

    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_INVENTORYMANAGER')")
    public ResponseEntity<Object> create(@RequestBody GoodsReceivedNoteDto grnDto, BindingResult bindingResult) {
        try{
            if(bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult);
            }
            grnService.createGRN(grnDto);
            return ResponseEntity.status(201).body("GRN created");
        }catch(Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/view/{grnId}")
    public ResponseEntity<Object> getGrnById(@PathVariable Long grnId) {
        try {
            GoodsReceivedNoteDto goodsReceivedNote = grnService.findGRNById(grnId); // Use instance method
            return new ResponseEntity<>(goodsReceivedNote, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goods Received Note not found with ID: " + grnId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Object> updateGRN(@RequestBody GoodsReceivedNoteDto grnDto,
                                            @PathVariable Long id,
                                            BindingResult bindingResult) {
        try{
            if(bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult);
            }
            grnService.updateGRN(grnDto, id);
            return ResponseEntity.status(201).body("GRN updated");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGRN(@PathVariable Long id) {
        try{

            grnService.deleteGRN(id);
            return ResponseEntity.status(201).body("GRN deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }

    }

}
