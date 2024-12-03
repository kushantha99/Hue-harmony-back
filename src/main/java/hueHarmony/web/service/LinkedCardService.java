package hueHarmony.web.service;

import hueHarmony.web.dto.LinkedCardDto;
import hueHarmony.web.model.Customer;
import hueHarmony.web.model.LinkedCard;
import hueHarmony.web.model.enums.LinkedCardStatus;
import hueHarmony.web.repository.LinkedCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkedCardService {

    private final LinkedCardRepository linkedCardRepository;

    @Transactional
    public LinkedCard handleLinkedCard(LinkedCardDto linkedCardDto, Customer customer){

        return switch (linkedCardDto.getLinkedCardChoice()) {
            case NEW -> LinkedCard.builder()
                    .token(linkedCardDto.getToken())
//                    .gatewayCustomerId(linkedCardDto.getGatewayCustomerId())
                    .cardOffset(linkedCardDto.getOffset())
                    .expireDate(linkedCardDto.getExpireDate())
                    .isDefault(false)
                    .linkedCardType(linkedCardDto.getCardType())
                    .linkedCardStatus(linkedCardDto.getStatus())
                    .retailCustomer(customer.getRetailCustomer())
                    .build();
            case DEFAULT -> LinkedCard.builder()
                    .token(linkedCardRepository.findDefaultLinkedCardByCustomerId(customer.getRetailCustomer().getRetailCustomerId(), LinkedCardStatus.AVAILABLE))
                    .build();
            case MENTIONED -> LinkedCard.builder()
                    .token(linkedCardRepository.findLinkedCardByCustomerIdAndPaymentMethodId(customer.getRetailCustomer().getRetailCustomerId(), linkedCardDto.getToken(), LinkedCardStatus.AVAILABLE))
                    .build();
        };

    }
}
