package org.edwinsoto.trackapplication.repository;

import org.edwinsoto.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadLocalRandom;

@Repository
@Profile("dev")
public class InMemoryPricingDAO implements PricingDAO {

    @Override
    public void addPrice(Track track) {
        int rating = ThreadLocalRandom.current().nextInt(0, 100);
        track.setPrice(rating);
    }
}
