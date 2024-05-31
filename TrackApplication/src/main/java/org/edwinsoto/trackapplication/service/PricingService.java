package org.edwinsoto.trackapplication.service;

import org.edwinsoto.trackapplication.model.Track;
import org.edwinsoto.trackapplication.dao.PricingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    private PricingDAO pricingDAO;

    @Autowired
    public PricingService(PricingDAO pricingDAO) {
        this.pricingDAO = pricingDAO;
    }

    public void addRandomPrice(Track track){
        pricingDAO.addPrice(track);
    }
}
