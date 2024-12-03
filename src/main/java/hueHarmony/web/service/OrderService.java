package hueHarmony.web.service;

import com.stripe.exception.StripeException;
import hueHarmony.web.dto.OrderDto;
import hueHarmony.web.model.*;
import hueHarmony.web.model.enums.LinkedCardChoice;
import hueHarmony.web.model.enums.PaymentMethod;
import hueHarmony.web.model.enums.PaymentStatus;
import hueHarmony.web.repository.CartItemRepository;
import hueHarmony.web.repository.OrderRepository;
import hueHarmony.web.util.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;
    private final CartItemRepository cartItemRepository;
    private final CustomerService customerService;
    private final LinkedCardService linkedCardService;
    private final StripeService stripeService;
    private final ProductService productService;
    private final RetailCustomerService retailCustomerService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createOnlineOrder(OrderDto orderDto) throws StripeException {
        Order order = createOrder(orderDto);

        Customer customer = handleOrderRetailCustomer(orderDto);

        order.setCustomer(customer);

        order.setOnlineOrder(
                OnlineOrder.builder()
                        .billingAddress(orderDto.getBillingAddress())
                        .shippingAddress(orderDto.getShippingAddress())
                        .order(order)
                        .deliveryCost(400.00f)
//                        .deliveryService()
//                        .district()
//                        .city()
                        .build()
        );

        LinkedCard linkedCard = null;
        switch (orderDto.getPaymentMethod()){
            case CARD:
                order.setOrderPayments(
                        List.of(
                            handleOrderPayment(
                                order,
                                orderDto.getPaymentMethod()
                            )
                        )
                );

                linkedCard = linkedCardService.handleLinkedCard(orderDto.getLinkedCardDto(), customer);

                if(orderDto.getLinkedCardDto().getLinkedCardChoice() == LinkedCardChoice.NEW)
                    customer.getRetailCustomer().getLinkedCards().add(linkedCard);

                break;
            case COD:
                break;
            default:
                throw new IllegalArgumentException("Invalid payment method.");
        }

        orderRepository.save(order);

        if(orderDto.getPaymentMethod() == PaymentMethod.CARD && linkedCard!=null && linkedCard.getToken()!=null && !linkedCard.getToken().isEmpty() && !linkedCard.getToken().isBlank()){
            stripeService.createPaymentIntent(
                    (long) Math.round(order.getOrderPayments().get(0).getPaymentAmount() * 100),
                    "lkr",
                    linkedCard.getToken(),
                    "Card payment for order number " + order.getOrderNo() + ".",
                    new HashMap<>(){{
                        put("paymentNumber", order.getOrderPayments().get(0).getPaymentNo());
                        put("orderNumber", order.getOrderNo());
                    }}
            );
        }else if(orderDto.getPaymentMethod() != PaymentMethod.COD){
            throw new IllegalArgumentException("Invalid payment method or payment method is blocked.");
        }
    }

    @Transactional
    public void createPosOrder(OrderDto orderDto){
        Order order = createOrder(orderDto);

        Customer customer = handleOrderRetailCustomer(orderDto);

        order.setCustomer(customer);

        switch (orderDto.getPaymentMethod()){
            case POS_CASH:
//                order.setPaymentStatus(PaymentStatus.PAID);
                break;
            case POS_CARD:
//                order.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
                break;
            default:
                throw new IllegalArgumentException("Invalid payment method.");
        }

        orderRepository.save(order);
    }

    @Transactional
    protected Order createOrder(OrderDto orderDto){
        Order order = Order.builder()
                .orderNote(orderDto.getOrderNote())
                .orderPaymentMethod(orderDto.getPaymentMethod())
                .createdUser(entityManager.getReference(User.class, jwtUtil.extractUserIdWithToken()))
//                .createdUser(entityManager.getReference(User.class, 3))
                .orderDiscount(orderDto.getDiscount())
                .build();

        List<OrderProduct> orderProducts;

        if (orderDto.getProductId() == 0 && !orderDto.getCartItems().isEmpty()) {
            // Case 1: productId is 0 and there are cart items
            orderProducts = orderDto.getCartItems().stream()
                    .map(cartItem -> {
                        long productId = cartItemRepository.getProductIdByCartItemId((long) cartItem.getCartItemId());
                        float[] prices = productService.getProductPriceAndDiscount(productId);

                        return OrderProduct.builder()
                                .product(entityManager.getReference(Product.class, productId))
                                .order(order)
                                .discount(prices[0])
                                .fullPrice(cartItem.getQuantity() * prices[1])
                                .quantity(cartItem.getQuantity())
                                .build();
                    })
                    .toList();

            cartItemRepository.deleteAllById(orderDto.getCartItems().stream().map(cartItem -> (long) cartItem.getCartItemId()).toList());
        } else if (orderDto.getProductId() != 0) {
            // Case 2: productId is not 0
            float[] prices = productService.getProductPriceAndDiscount(orderDto.getProductId());

            orderProducts = List.of(
                    OrderProduct.builder()
                        .product(entityManager.getReference(Product.class, orderDto.getProductId()))
                        .discount(prices[0])
                        .fullPrice(orderDto.getQuantity() * prices[1])
                        .order(order)
                        .quantity(orderDto.getQuantity())
                        .build()
            );
        } else {
            // Case 3: Default case, neither condition is satisfied
            throw new IllegalStateException("No items found for create the order.");
        }

//        order.getOrderProducts().addAll(orderProducts);
        order.setOrderProducts(orderProducts);

        return order;
    }

    @Transactional
    protected Payment handleOrderPayment(Order order, PaymentMethod paymentMethod){
        //        order.setOrderPayments(List.of(payment));

        return Payment.builder()
                .paymentStatus(paymentMethod == PaymentMethod.POS_CASH ? PaymentStatus.PAID : PaymentStatus.PENDING)
                .paymentDescription("Payment for order number "+order.getOrderNo()+".")
                .paymentAmount(
                        order.getOrderProducts().stream()
                                .map(OrderProduct::getFullPrice)
                                .reduce(0.0f, Float::sum) +
                        order.getOnlineOrder().getDeliveryCost()
                )
                .build();
    }

    @Transactional
    protected Customer handleOrderRetailCustomer(OrderDto orderDto){
        Long customerId = retailCustomerService.getCustomerIdByUserId(jwtUtil.extractUserIdWithToken());
        Customer customer;
        if(
                customerId==0 &&
                !orderDto.getEmailAddress().isEmpty() &&
                !orderDto.getFirstName().isEmpty() &&
                !orderDto.getLastName().isEmpty() &&
                !orderDto.getContactNos().isEmpty()
        ){
            customer = Customer.builder()
                    .firstName(orderDto.getFirstName())
                    .lastName(orderDto.getLastName())
                    .email(orderDto.getEmailAddress())
                    .contactNos(orderDto.getContactNos())
                    .build();

            RetailCustomer retailCustomer = RetailCustomer.builder()
                .customer(customer)
                .user(entityManager.getReference(User.class, jwtUtil.extractUserIdWithToken()))
//              .user(entityManager.getReference(User.class, 3))
                .build();

            customer.setRetailCustomer(retailCustomer);
        }else{
            customer = entityManager.getReference(Customer.class, customerId);
        }

        return customer;
    }


}
