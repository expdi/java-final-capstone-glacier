package org.glacier.pricingapplication.controller;

import org.glacier.pricingapplication.service.PricingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    void testSetLimitsValid(){
        ResponseEntity<?> response = pricingController.setLimitsPrice(50,58);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testSetLimitsInvalid(){
        ResponseEntity<?> response = pricingController.setLimitsPrice(100,10);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testSetLimitsInvalidEqual(){
        ResponseEntity<?> response = pricingController.setLimitsPrice(10,10);
        assertTrue(response.getStatusCode().is4xxClientError());
    }
}