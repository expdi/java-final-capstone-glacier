package org.glacier.trackapplication.dao.pricing;

import org.glacier.trackapplication.dao.PricingDAO;
import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadLocalRandom;

@Repository
@Profile("pricing_inmem")
public class InMemoryPricingDAO implements PricingDAO {

    @Override
    public void addPrice(Track track) {
        int rating = ThreadLocalRandom.current().nextInt(0, 100);
        track.setPrice(rating);
    }
}
