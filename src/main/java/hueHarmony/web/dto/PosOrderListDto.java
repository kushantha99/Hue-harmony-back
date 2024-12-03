package hueHarmony.web.dto;

import hueHarmony.web.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosOrderListDto {
    private Long orderNumber;
    private float total;
    private float subTotal;
    private float discount;
    private Date orderDate;
    private OrderStatus orderStatus;

}
