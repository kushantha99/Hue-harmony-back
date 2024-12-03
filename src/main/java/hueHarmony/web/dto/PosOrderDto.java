package hueHarmony.web.dto;

import hueHarmony.web.model.PosOrderItem;
import hueHarmony.web.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosOrderDto {

    private Long id;
    private Integer phoneNumber;
    private Date orderDate;
    private List<PosOrderItemDto> items; // Use a corresponding DTO for PosOrderItem
    private float total;
    private float subTotal;
    private float discount;
    private String paymentMethod;
    private OrderStatus orderStatus;
    private Long cashierId; // Assuming only the ID of the cashier is needed
}

