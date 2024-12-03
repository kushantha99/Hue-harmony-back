package hueHarmony.web.service;

import hueHarmony.web.model.WholeSaleInvoice;
import hueHarmony.web.model.WholeSaleOrder;
import hueHarmony.web.repository.InvoiceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class InvoiceService {


    private final InvoiceRepository invoiceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void generateInvoice(WholeSaleOrder order) {
        WholeSaleInvoice invoice = WholeSaleInvoice.builder()
                .wholeSaleOrder(entityManager.getReference(WholeSaleOrder.class,order.getOrderId()))
                .invoiceDate(new Date())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .netAmount(order.getTotalAmount() - (order.getDiscountAmount() != null ? order.getDiscountAmount() : 0))
                .billingAddress(order.getBillingAddress())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .notes(order.getOrderNotes())
                .build();

        order.setInvoice(invoice);

         invoiceRepository.save(invoice);
    }
}
