package hueHarmony.web.repository;

import hueHarmony.web.dto.LinkedCardDto;
import hueHarmony.web.model.LinkedCard;
import hueHarmony.web.model.enums.LinkedCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkedCardRepository extends JpaRepository<LinkedCard, Long> {

//    @Query(value = "SELECT NEW hueHarmony.web.dto.LinkedCardDto(" +
//            "lc.token," +
//            "lc.gatewayCustomerId," +
//            "lc.cardOffset," +
//            "lc.linkedCardType," +
//            "lc.linkedCardStatus" +
//            ") FROM LinkedCard lc" +
//            " WHERE lc.retailCustomer.customer.customerId = :id")
//    List<LinkedCardDto> findLinkedCardsByCustomerId(@Param("id")long id);

    @Query(value = "UPDATE LinkedCard l SET l.token = :token, l.linkedCardStatus = :status, l.gatewayCustomerId = :custId WHERE l.linkedCardId = :id")
    void updateTokenByLinkedCardId(@Param("token")String token, @Param("custId")String custId, @Param("id")long id, @Param("status") LinkedCardStatus status);

    @Query("SELECT lc.linkedCardId FROM LinkedCard lc WHERE lc.retailCustomer.customer.customerId = :customerId AND lc.isDefault AND lc.linkedCardStatus = :linkedCardStatus")
    String findDefaultLinkedCardByCustomerId(@Param("customerId")int customerId, @Param("linkedCardStatus")LinkedCardStatus linkedCardStatus);

    @Query("SELECT lc.linkedCardId FROM LinkedCard lc WHERE lc.retailCustomer.customer.customerId = :customerId AND lc.gatewayCustomerId = :paymentMethodId AND lc.linkedCardStatus = :linkedCardStatus")
    String findLinkedCardByCustomerIdAndPaymentMethodId(@Param("customerId")int customerId, @Param("paymentMethodId")String paymentMethodId, @Param("linkedCardStatus")LinkedCardStatus linkedCardStatus);
}
