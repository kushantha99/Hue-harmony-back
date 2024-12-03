package hueHarmony.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesSummaryDto {

    private float Cash;
    private float Debit;
    private float Discount;
    private float Total;

}
