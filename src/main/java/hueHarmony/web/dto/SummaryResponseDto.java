package hueHarmony.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponseDto {

    private BigDecimal total;
    private BigDecimal cash;
    private BigDecimal card;
    private BigDecimal discount;
}
