package org.glacier.trackapplication.service;

import org.glacier.trackapplication.dao.PricingDAO;
import org.glacier.trackapplication.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    private final PricingDAO pricingDAO;

    @Autowired
    public PricingService(PricingDAO pricingDAO) {
        this.pricingDAO = pricingDAO;
    }

    public void addRandomPrice(Track track){
        pricingDAO.addPrice(track);
    }
}
