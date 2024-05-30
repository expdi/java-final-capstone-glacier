package org.edwinsoto.pricingapplication.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PricingService {

    public Integer getPrice(Integer id){
        Integer price = ThreadLocalRandom.current().nextInt(1, 10);
        return price;
    }
}
