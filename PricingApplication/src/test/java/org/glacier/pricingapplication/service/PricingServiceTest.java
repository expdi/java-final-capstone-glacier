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
}