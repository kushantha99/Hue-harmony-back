package hueHarmony.web.service;

import hueHarmony.web.repository.RetailCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RetailCustomerService {

    private final RetailCustomerRepository retailCustomerRepository;

    @Transactional(readOnly = true)
    public Long getCustomerIdByRetailCustomerId(long customerId) {
        return retailCustomerRepository.getCustomerIdByRetailCustomerId(customerId).orElseThrow(() -> new IllegalStateException("Retail customer not found."));
    }

    @Transactional(readOnly = true)
    public Long getCustomerIdByUserId(int userId) {
        return retailCustomerRepository.getCustomerIdByUserId(userId).orElseThrow(() -> new IllegalStateException("Retail customer not found."));
    }
}
