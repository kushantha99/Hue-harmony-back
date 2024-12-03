package hueHarmony.web.controller;

import hueHarmony.web.dto.CustomerDto;
import hueHarmony.web.dto.FilterCustomerDto;
import hueHarmony.web.dto.FilterOrderDto;
import hueHarmony.web.dto.WholeSaleCustomerDto;
import hueHarmony.web.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class Customer {

    private final CustomerService customerService;

    @GetMapping("/view/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE', 'ROLE_SALESMANAGER')")
    public ResponseEntity<Object> viewCustomer(@PathVariable("customerId") int customerId) {
        try{
            return ResponseEntity.status(200).body("Supplier status update successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE', 'ROLE_SALESMANAGER')")
    public ResponseEntity<Object> filterCustomer(
            @Validated(FilterOrderDto.whenOrganization.class) @ModelAttribute FilterCustomerDto request,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "select", defaultValue = "all") String select,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "sort", defaultValue = "id,asc") String sort
    ) {
        try{
            return ResponseEntity.status(200).body("Supplier status update successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/create/wholesale-customer")
// @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
    public ResponseEntity<Object> createCustomer(@RequestBody WholeSaleCustomerDto customerDto) {
        try {
            // Calling the service to create the customer
            customerService.createCustomer(customerDto);

            // Returning the response with status 201 (CREATED) and a success message
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully.");

        } catch (Exception e) {
            // Log the exception details for debugging (optional)
            // logger.error("Error occurred while creating customer", e);

            // Returning the response with status 500 (INTERNAL_SERVER_ERROR) and a generic error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: " + e.getMessage());
        }
    }
    @PostMapping("/create/wholesale/new")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
    public ResponseEntity<Object> createNewWholeSaleCustomer(
            @Validated({WholeSaleCustomerDto.onCreation.class, CustomerDto.onCreation.class}) @RequestBody WholeSaleCustomerDto customer,
            BindingResult bindingResult
    ) {
        try{
            return ResponseEntity.status(200).body("Supplier status update successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/create/wholesale/old")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
    public ResponseEntity<Object> createOldWholeSaleCustomer(
            @Validated({WholeSaleCustomerDto.onCreation.class, CustomerDto.onOldCreation.class}) @RequestBody WholeSaleCustomerDto customer,
            BindingResult bindingResult
    ) {
        try{
            return ResponseEntity.status(200).body("Supplier status update successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/update/wholesale")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
    public ResponseEntity<Object> updateWholeSaleCustomer(
            @Validated({WholeSaleCustomerDto.onUpdate.class, CustomerDto.onUpdate.class}) @RequestBody WholeSaleCustomerDto customer,
            BindingResult bindingResult
    ) {
        try{
            return ResponseEntity.status(200).body("Supplier status update successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
    public ResponseEntity<Object> updateCustomer(
            @Validated({CustomerDto.onUpdate.class}) @RequestBody CustomerDto customer,
            BindingResult bindingResult
    ) {
        try{
            return ResponseEntity.status(200).body("Supplier status update successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE')")
    public ResponseEntity<Object> deleteWholeSaleCustomer(
            @Validated(WholeSaleCustomerDto.onDelete.class) @RequestBody WholeSaleCustomerDto customer,
            BindingResult bindingResult
    ) {
        try{
            return ResponseEntity.status(200).body("Supplier status update successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> approveWholeSaleCustomer(
            @Validated(WholeSaleCustomerDto.onStatusUpdate.class) @RequestBody WholeSaleCustomerDto customer,
            BindingResult bindingResult
    ) {
        try{
            return ResponseEntity.status(200).body("Supplier status update successfully.");

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
