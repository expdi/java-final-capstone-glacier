package org.glacier.pricingapplication.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Getter
@Setter
public class PricingService {
    @Value("${glacier.pricing.lowerLimit}")
    private int lowerLimit;
    @Value("${glacier.pricing.upperLimit}")
    private int upperLimit;

    public Integer getPrice(Integer id){
        Integer price = ThreadLocalRandom.current().nextInt(lowerLimit, upperLimit);
        return price;
    }
}
