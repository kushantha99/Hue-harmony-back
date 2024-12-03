package hueHarmony.web.repository;

import hueHarmony.web.model.Payment;
import hueHarmony.web.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    @Query("UPDATE Payment p SET p.paymentStatus = :paymentStatus WHERE p.paymentNo = :paymentNumber")
    void changePaymentStatusByPaymentNumberAndPaymentStatus(@Param("paymentNumber") String paymentNumber, @Param("paymentStatus") PaymentStatus paymentStatus);
}
