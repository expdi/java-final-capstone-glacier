package org.edwinsoto.pricingapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.edwinsoto.pricingapplication.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/pricing")
public class PricingController {

    private final PricingService service;

    @Autowired
    public PricingController(PricingService service) {
        this.service = service;
    }

    @Operation(summary = "Get pricing based on ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Track Pricing", content = { @Content(mediaType = "application/json")}),
    })
    @GetMapping("/id={id}")
    public ResponseEntity<Integer> getPricing(@PathVariable Integer id){
        Integer val = service.getPrice(id);
        return ResponseEntity.ok().body(val);
    }
}
