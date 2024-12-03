package hueHarmony.web.repository;


import hueHarmony.web.model.WholeSaleInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<WholeSaleInvoice,Long> {

}
