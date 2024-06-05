package org.glacier.trackapplication.repository.pricing;

import org.glacier.trackapplication.model.Track;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("pricing_inmem")
@ExtendWith(SpringExtension.class)
class InMemoryPricingDAOTest {

    @Autowired
    private InMemoryPricingDAO pricingDAO;

    @Test
    @Disabled("To Be Continued")
    void addPrice() {
        Track track = Track.builder().id(1).title("Hello").build();
        pricingDAO.addPrice(track);
        assertTrue(track.getPrice()>0);
    }
}