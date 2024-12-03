package hueHarmony.web.controller.web_hook;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import hueHarmony.web.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class StripeWebHook {

    private final static String WEBHOOK_SECRET = "whsec_1ZcTXRdxOQ2PqBiNbQ0yXwF5rlvPovSq";
    private final static String STRIPE_SIGNATURE = "Stripe-Signature";

    private final PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader(STRIPE_SIGNATURE) String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, WEBHOOK_SECRET);

            if ("payment_intent.succeeded".equals(event.getType())) {
                Optional<StripeObject> stripeObject = event.getDataObjectDeserializer().getObject();

                if (stripeObject.isEmpty()) {
                    throw new IllegalArgumentException("No payment intent found in the event.");
                }

                // Access metadata
                Map<String, String> metadata = ((PaymentIntent) stripeObject.get()).getMetadata();

                // Example: Logging metadata
                if (metadata != null && !metadata.isEmpty() && metadata.containsKey("paymentNumber")) {
                    String paymentNumber = metadata.get("paymentNumber");

                    if(paymentNumber == null || paymentNumber.isEmpty() || paymentNumber.isBlank()){
                        throw new IllegalArgumentException("No metadata found in the payment intent.");
                    }

                    paymentService.changeProductStatus(paymentNumber);
                }
            }

            return ResponseEntity.ok("Webhook received.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
