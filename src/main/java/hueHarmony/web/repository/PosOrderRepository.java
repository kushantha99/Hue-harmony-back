package hueHarmony.web.repository;

import hueHarmony.web.dto.PosOrderListDto;
import hueHarmony.web.model.PosOrder;
import hueHarmony.web.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PosOrderRepository extends JpaRepository<PosOrder,Long> {

//    static List<PosOrder> findByCashierIdAndTimestampBetween(Long cashierId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
//    }

    @Query("SELECT p FROM PosOrder p WHERE p.cashierId = :cashierId AND p.orderDate BETWEEN :startDate AND :endDate")
     List<PosOrder> findByCashierAndOrderDateBetween(@Param("cashierId") Long cashierId,
                                                           @Param("startDate") LocalDateTime startDate,
                                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM PosOrder p WHERE p.cashierId = :cashierId AND p.orderStatus = :orderStatus")
    List<PosOrder> findByCashierAndOrderStatus(@Param("cashierId") Long cashierId, @Param("orderStatus") OrderStatus orderStatus);
}
