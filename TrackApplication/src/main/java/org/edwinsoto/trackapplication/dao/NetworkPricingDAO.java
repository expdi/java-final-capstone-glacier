package org.edwinsoto.trackapplication.dao;

import org.apache.tomcat.util.codec.binary.Base64;
import org.edwinsoto.trackapplication.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
@Profile("prod")
public class NetworkPricingDAO implements PricingDAO {

    private RestClient restClient;

    private String pricingURL;

    @Autowired
    private Environment env;

    public NetworkPricingDAO() {
        var baseUrl = "http://localhost:8081";
        var endpoint = "api/v1/pricing";
        pricingURL = endpoint + "/id={id}";

        String userName = env.getProperty("SPRING_ADMIN_USERNAME");
        String password = env.getProperty("SPRING_ADMIN_PASSWORD");
        String plainCreds = userName + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes, true);
        String base64Creds = new String(base64CredsBytes);

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + base64Creds)

                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Content-Type", userName)
                .defaultHeader("Content-Type", password)
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
