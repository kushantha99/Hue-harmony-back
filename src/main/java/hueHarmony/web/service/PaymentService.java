package hueHarmony.web.service;

import hueHarmony.web.model.enums.PaymentStatus;
import hueHarmony.web.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void changeProductStatus(String paymentNumber){

        paymentRepository.changePaymentStatusByPaymentNumberAndPaymentStatus(paymentNumber, PaymentStatus.PAID);
    }
}
