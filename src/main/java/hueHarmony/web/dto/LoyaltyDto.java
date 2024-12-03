package hueHarmony.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyDto {

    private String contactNo;
    private String loyaltyStatus;
    private float loyaltyPoints;

}
