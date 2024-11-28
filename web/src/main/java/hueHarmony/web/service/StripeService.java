package hueHarmony.web.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StripeService {

    public PaymentIntent createPaymentIntent(Long amount, String currency, String paymentMethodId, String description, Map<String, String> metadata) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // Amount in cents
                .setCurrency(currency)
                .setPaymentMethod(paymentMethodId)
                .setConfirm(true)  // Automatically confirm the payment
                .setDescription(description)
                .putAllMetadata(metadata)
                .build();

        return PaymentIntent.create(params);
    }
}
