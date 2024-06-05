package org.edwinsoto.trackapplication.dao;

import org.edwinsoto.trackapplication.model.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Repository
@Profile("prod")
public class NetworkPricingDAO implements PricingDAO {

    private RestClient restClient;

    private String pricingURL;

    @Value("${env.user.regularuser}")
    private String userName;

    @Value("${env.user.regularuserpassword}")
    private String password;

    public NetworkPricingDAO() {
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
        var baseUrl = "http://localhost:8081";
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
