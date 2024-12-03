package hueHarmony.web.dto;

import hueHarmony.web.model.enums.CardType;
import hueHarmony.web.model.enums.LinkedCardChoice;
import hueHarmony.web.model.enums.LinkedCardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkedCardDto {

    private String token;
    private String offset;
    private CardType cardType;
    private LinkedCardStatus status = LinkedCardStatus.AVAILABLE;
    private String expireDate;
    private LinkedCardChoice linkedCardChoice;

    public LinkedCardDto(String token, String offset, CardType cardType, LinkedCardStatus linkedCardStatus){
        this.token = token;
        this.offset = offset;
        this.cardType = cardType;
        this.status = linkedCardStatus;
    }
}
