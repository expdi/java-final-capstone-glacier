package org.glacier.pricingapplication.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PricingServiceTest {

    @Autowired
    private PricingService pricingService;

    @Test
    void getPrice() {

        pricingService.setLowerLimit(1);
        pricingService.setUpperLimit(10);

        Integer price = pricingService.getPrice(2);
        assertNotNull(price);
    }

    @Test
    void voidTestSetLowerLimits() {
        Integer prevPrice = pricingService.getLowerLimit();

        pricingService.setLowerLimit(10);

        Integer curPrice = pricingService.getLowerLimit();

        assertNotEquals(prevPrice, curPrice);

    }

    @Test
    void voidTestSetUpperLimits() {
        Integer prevPrice = pricingService.getUpperLimit();
        pricingService.setUpperLimit(20);

        Integer curPrice = pricingService.getUpperLimit();

        assertNotEquals(prevPrice, curPrice);
    }

}