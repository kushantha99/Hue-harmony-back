package hueHarmony.web.repository;

import hueHarmony.web.model.Supplier;
import hueHarmony.web.model.enums.SupplierStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT COUNT(s) > 0 FROM Supplier s WHERE s.supplierId = :supplierId AND s.supplierStatus = 'ACTIVE'")
    boolean checkSupplierStatusBySupplierId(@Param("supplierId") Long supplierId);

    @Modifying
    @Query("UPDATE Supplier s SET s.supplierStatus = :supplierStatus WHERE s.supplierId = :supplierId")
    void updateSupplierStatusBySupplierId(@Param("supplierId") Long supplierId, @Param("supplierStatus") SupplierStatus supplierStatus);

}
