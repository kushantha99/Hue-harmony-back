package hueHarmony.web.util;

import org.springframework.context.annotation.Bean;

public class FunctionsUtil {
    public static int calculateLoyaltyPoints(float totalAmountSpent) {
        int pointsPerDollar = 1; // 1 point for every $10 spent
        return (int) (totalAmountSpent / 250) * pointsPerDollar;
    }

    // Calculate the discount based on loyalty points
    public static double calculateDiscountFromPoints(Integer loyaltyPoints) {
        int pointsPerDollar = 100; // 100 points = $1
        double discountPerPoint = 1000.0 / pointsPerDollar; // Each point's discount value
        return loyaltyPoints * discountPerPoint;
    }
}
