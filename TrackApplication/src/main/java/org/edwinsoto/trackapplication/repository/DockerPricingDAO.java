package org.edwinsoto.trackapplication.repository;

import org.edwinsoto.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
@Profile("docker")
public class DockerPricingDAO implements PricingDAO {

    private RestClient restClient;

    private String pricingURL;

    public DockerPricingDAO() {
        var baseUrl = "http://pricing-api:8081";
        var endpoint = "api/v1/pricing";
        pricingURL = endpoint + "/id={id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    public void addPrice(Track track) {
        ResponseEntity<Integer> result = restClient.get()
                .uri(pricingURL, 1)
                .retrieve()
                .toEntity(Integer.class);

        int pricing = result.getBody().intValue();
        track.setPrice(pricing);

    }
}
