package org.glacier.trackapplication.repository.pricing;
import org.glacier.trackapplication.repository.PricingDAO;
import org.glacier.trackapplication.model.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Repository
@Profile("pricing_docker")
public class DockerPricingDAO implements PricingDAO {

    private RestClient restClient;

    private String pricingURL;

    @Value("superUser")
    private String userName;

    @Value("password1")
    private String password;

    public DockerPricingDAO() {
        var endpoint = "api/v1/pricing";
        pricingURL = endpoint + "/id={id}";
    }

    @Override
    public void addPrice(Track track) {
        setRestClient();

        ResponseEntity<Integer> result = restClient.get()
                .uri(pricingURL, 1)
                .retrieve()
                .toEntity(Integer.class);

        int pricing = result.getBody().intValue();
        track.setPrice(pricing);
    }

    private void setRestClient() {
        if (this.restClient != null) {
            return;
        }
        var baseUrl = "http://pricing-api:8081";
        String valueToEncode = userName + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(valueToEncode.getBytes());
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + base64Creds)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
