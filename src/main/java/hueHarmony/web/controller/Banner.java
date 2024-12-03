package hueHarmony.web.controller;

import hueHarmony.web.dto.BannerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banner")
public class Banner {

//    @GetMapping("/list")
////    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE', 'ROLE_USER')")
//    public ResponseEntity<Object> getBanners() {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> createBanner(@Validated({BannerDto.onCreate.class}) @RequestPart("banner")BannerDto bannerDto) {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @GetMapping("/update")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> updateBanner(@Validated({BannerDto.onCreate.class}) @RequestPart("banner")BannerDto bannerDto) {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @GetMapping("/delete")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
//    public ResponseEntity<Object> deleteBanner(@Validated({BannerDto.onCreate.class}) @RequestPart("banner")BannerDto bannerDto) {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
//    @GetMapping("/approve")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    public ResponseEntity<Object> approveBanner(@Validated({BannerDto.onCreate.class}) @RequestPart("banner")BannerDto bannerDto) {
//        try{
//
//        }catch(Exception e){
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
}
