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
        Integer price = pricingService.getPrice(1);
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
        pricingService.setUpperLimit(10);

        Integer curPrice = pricingService.getUpperLimit();

        assertNotEquals(prevPrice, curPrice);
    }

}