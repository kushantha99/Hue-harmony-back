package hueHarmony.web.repository;

import hueHarmony.web.model.RetailCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetailCustomerRepository extends JpaRepository<RetailCustomer, Long> {

    @Query("SELECT rc.customer.customerId FROM RetailCustomer rc WHERE rc.retailCustomerId = :retailCustomerId")
    Optional<Long> getCustomerIdByRetailCustomerId(@Param("retailCustomerId") long retailCustomerId);

    @Query("SELECT rc.customer.customerId FROM RetailCustomer rc WHERE rc.user.userId = :userId")
    Optional<Long> getCustomerIdByUserId(@Param("userId") int userId);
}
