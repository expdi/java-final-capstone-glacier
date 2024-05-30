package org.edwinsoto.pricingapplication.controller;

import org.edwinsoto.pricingapplication.service.PricingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingControllerTest {

    @InjectMocks
    private PricingController pricingController;

    @Mock
    private PricingService pricingService;

    @Test
    void testgetPricing() {
        when(pricingService.getPrice(1)).thenReturn(3);
        ResponseEntity<Integer> response = pricingController.getPricing(1);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}