package hueHarmony.web.config;

import com.stripe.Stripe;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class StripeConfig {
    @PostConstruct
    public void init() {
        // Set the API secret key
        Stripe.apiKey = "sk_test_51QPrbREUxK0KeTafH3MlzD6mVCq2h9NqwwARZ0aIY0GxoQUkA2Wwxw62gqBLGdmRoPY8jpWTNaWOYLpHG1yIDuoL00Lt6L2ljF";  // Replace with your Stripe secret key
    }
}

