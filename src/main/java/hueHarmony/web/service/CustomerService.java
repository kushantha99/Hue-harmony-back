package hueHarmony.web.service;

import hueHarmony.web.dto.CustomerDto;
import hueHarmony.web.dto.WholeSaleCustomerDto;
import hueHarmony.web.model.Customer;
import hueHarmony.web.model.WholeSaleCustomer;
import hueHarmony.web.model.enums.LinkedCardStatus;
import hueHarmony.web.repository.CustomerRepository;
import hueHarmony.web.repository.LinkedCardRepository;
import hueHarmony.web.repository.RetailCustomerRepository;
import hueHarmony.web.repository.WholeSaleCustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RetailCustomerRepository retailCustomerRepository;
    private final WholeSaleCustomerRepository wholeSaleCustomerRepository;
    private final LinkedCardRepository linkedCardRepository;

    @PersistenceContext
    private  EntityManager entityManager;

    public boolean isCustomerExist(int customerId){
        return customerRepository.existsById((long) customerId);
    }

    public boolean isRetailCustomerExist(int retailCustomerId){
        return customerRepository.existsById((long) retailCustomerId);
    }

    public boolean isWholeSaleCustomerExist(int wholeSaleCustomerId){
        return customerRepository.existsById((long) wholeSaleCustomerId);
    }

//    public boolean isValidCustomerId(WholeSaleCustomerDto wholeSaleCustomerDto, String key){
//        return wholeSaleCustomerRepository.checkCustomerIdAndWholeSaleCustomerIdAreLinked((long) wholeSaleCustomerDto.getCustomerDto().getCustomerId(), (long) wholeSaleCustomerDto.getWholeSaleCustomerId());
//    }

    public String findDefaultLinkedCardByCustomerId(int customerId){
        try {
            String paymentMethodId = linkedCardRepository.findDefaultLinkedCardByCustomerId(customerId, LinkedCardStatus.AVAILABLE);
            if(paymentMethodId==null || paymentMethodId.isEmpty() || paymentMethodId.isBlank()) return null;
            return paymentMethodId;
        }catch (Exception exception){
            return null;
        }
    }

    @Transactional
    public ResponseEntity<?> createCustomer(WholeSaleCustomerDto wholeSaleCustomerDto) {
        // Create the WholeSaleCustomer entity from the DTO
        WholeSaleCustomer wholesaleCustomer = WholeSaleCustomer.builder()
                .address(wholeSaleCustomerDto.getAddress())
                .nicNo(wholeSaleCustomerDto.getNicNo())
                .businessName(wholeSaleCustomerDto.getBusinessName())
                .contactPerson(wholeSaleCustomerDto.getContactPerson())
                .landPhone(wholeSaleCustomerDto.getLandPhone())
                .deliveryAddress(wholeSaleCustomerDto.getDeliveryAddress())
                .contactPersonNumber(wholeSaleCustomerDto.getContactPersonNumber())
                .build();

        // Create the Customer entity from the CustomerDto
        CustomerDto customerDto = wholeSaleCustomerDto.getCustomerDto();
        Customer customer = Customer.builder()
                .firstName(customerDto.getFirstName())
                .contactNos(customerDto.getContactNos())
                .email(customerDto.getEmail())
                .lastName(customerDto.getLastName())
                .build();

        // Set the relationship between customer and wholeSaleCustomer
        customer.setWholeSaleCustomer(wholesaleCustomer);
        wholesaleCustomer.setCustomer(customer);

        // Save the customer and associated wholeSaleCustomer
        customerRepository.save(customer);

        // Return a successful response with the created customer data
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }


}
