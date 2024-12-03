package hueHarmony.web.repository;

import hueHarmony.web.model.WholeSaleCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WholeSaleCustomerRepository extends JpaRepository<WholeSaleCustomer, Long> {

    @Query("SELECT COUNT(wsc)>0 FROM WholeSaleCustomer wsc WHERE wsc.wholeSaleCustomerId = :wholeSaleCustomerId AND wsc.customer.customerId = :customerId")
    boolean checkCustomerIdAndWholeSaleCustomerIdAreLinked(@Param("customerId") Long id, @Param("wholeSaleCustomerId") Long wholeSaleCustomerId);
}
