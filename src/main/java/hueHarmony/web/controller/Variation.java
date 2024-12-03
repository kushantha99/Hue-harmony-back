package hueHarmony.web.controller;

import hueHarmony.web.dto.FilterOrderDto;
import hueHarmony.web.dto.FilterVariationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variation")
public class Variation {

//    @GetMapping("/view/{variationId}")
//    @PreAuthorize("hasAnyRole('ROLE_INVENTORYMANAGER', 'ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> viewVariation(@PathVariable("variationId") int variationId) {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @GetMapping("/filter")
//    @PreAuthorize("hasAnyRole('ROLE_INVENTORYMANAGER', 'ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> filterVariation(@Validated(FilterOrderDto.whenOrganization.class) @ModelAttribute FilterVariationDto request) {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @PostMapping("/create")
//    @PreAuthorize("hasRole('ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> createVariation() {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @PostMapping("/update")
//    @PreAuthorize("hasRole('ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> updateVariation() {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @DeleteMapping("/delete")
//    @PreAuthorize("hasRole('ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> deleteVariation() {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
}
