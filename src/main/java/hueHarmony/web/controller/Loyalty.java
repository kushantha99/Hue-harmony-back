package hueHarmony.web.controller;

import hueHarmony.web.dto.LoyaltyDto;
import hueHarmony.web.dto.LoyaltyResponseDto;
import hueHarmony.web.service.LoyaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/loyalty")
@RequiredArgsConstructor
public class Loyalty {

    private final LoyaltyService loyaltyService;

    @GetMapping("/{contactNo}")
    public ResponseEntity<LoyaltyResponseDto> getLoyaltyDetails(@PathVariable BigDecimal contactNo) {
        try {
            LoyaltyResponseDto loyaltyDto = loyaltyService.getLoyaltyDiscount(contactNo);
            return ResponseEntity.ok(loyaltyDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
//    @PostMapping
//    public ResponseEntity<LoyaltyDto> saveLoyaltyDetails(@RequestBody LoyaltyDto loyaltyDto) {
//        LoyaltyDto savedDto = loyaltyService.saveLoyaltyDetails(loyaltyDto);
//        return ResponseEntity.ok(savedDto);
//    }

//    @DeleteMapping("/{contactNo}")
//    public ResponseEntity<String> deleteLoyalty(@PathVariable String contactNo) {
//        loyaltyService.deleteLoyalty(contactNo);
//        return ResponseEntity.ok("Loyalty record deleted successfully.");
//    }






//
//    @GetMapping("/add-to-loyalty/{contact-no}")
//    @PreAuthorize("hasRole('ROLE_CACHIER')")
//    public ResponseEntity<Object> addToLoyalty(@PathVariable("contact-no") String contactNo){
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @GetMapping("/check-my-loyalty")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<Object> addToLoyalty(){
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @PostMapping("/create-range")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SALESMANAGER', 'ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> addNewLoyaltyRange() {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @PostMapping("/update-range")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SALESMANAGER', 'ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> updateLoyaltyRange() {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @PostMapping("/update-discount-rate")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SALESMANAGER', 'ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> updateDDiscountRate() {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @GetMapping("/view-ranges")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SALESMANAGER', 'ROLE_BACKOFFICE', 'ROLE_CACHIER', 'ROLE_USER')")
//    public ResponseEntity<Object> viewLoyaltyRanges() {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
}
