package org.glacier.pricingapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.glacier.pricingapplication.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/setLimits/{lowerLimit}/{upperLimit}")
    public ResponseEntity<?> setLimitsPrice(@PathVariable("lowerLimit") int lowerLimit,
                                           @PathVariable("upperLimit") int upperLimit) {
        synchronized (this) {
            if (lowerLimit < upperLimit) {
                service.setLowerLimit(lowerLimit);
                service.setUpperLimit(upperLimit);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().body("Lower Limit can't be less than Upper Limit: "
                        + lowerLimit + ":" + upperLimit);
            }
        }
    }
}
