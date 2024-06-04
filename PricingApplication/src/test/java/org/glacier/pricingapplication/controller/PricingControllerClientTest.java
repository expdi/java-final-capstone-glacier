package org.glacier.pricingapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingControllerClientTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private RestClient restClient;

    private String baseUrl;
    private String rootUrl;
    private String pricingURL;

    @PostConstruct
    public void init() {
        var baseUrl = "http://localhost:" + port;
        var endpoint = "api/v1/pricing";
        pricingURL = endpoint + "/id={id}";

        //TODO: Obtaining values from environment variables
        String userName = "regularUser"; //env.getProperty("env.user.name");
        String password = "password2"; //env.getProperty("env.user.password");

        String valueToEncode = userName + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(valueToEncode.getBytes());
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + base64Creds)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Test
    public void getPricingForTrackTest() throws IOException {

        ResponseEntity<Integer> response = getRestClient("regularUser", "password2").get()
                .uri("api/v1/pricing/id={id}", 1).retrieve()
                .toEntity(Integer.class);

       assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"superUser, password1", "regularUser, password2"})
    public void getPricingForTrackMultipleUsers(String user, String password) throws IOException {

        ResponseEntity<Integer> response = getRestClient(user, password).get()
                .uri("api/v1/pricing/id={id}", 1).retrieve()
                .toEntity(Integer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPricingForTrackByUnauthorizedUser() throws IOException {

        assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            ResponseEntity<Integer> response = getRestClient("fakeUser", "passfake").get()
                    .uri("api/v1/pricing/id={id}", 1)
                    .retrieve()
                    .toEntity(Integer.class);
        });
    }

    @Test
    public void updateLimitForPricingByAuthorizedUser() throws IOException {

        ResponseEntity<?> response = getRestClient("superUser", "password1").put()
                .uri("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 1, 10).retrieve()
                .toEntity(ResponseEntity.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void updateLimitForPricingByUnauthorizedUser() throws IOException {

        assertThrows(HttpClientErrorException.Forbidden.class, () -> {
            ResponseEntity<?> response = getRestClient("regularUser", "password2").put()
                    .uri("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 1, 10).retrieve()
                    .toEntity(ResponseEntity.class);;
        });
    }

    @Test
    public void updateLimitWithInvalidRangeForPricingByAuthorizedUser() throws IOException {


        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            ResponseEntity<?> response = getRestClient("superUser", "password1").put()
                    .uri("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 10, 10).retrieve()
                    .toEntity(ResponseEntity.class);
        });
    }

    private RestClient getRestClient(String userName, String password) {
        var baseUrl = "http://localhost:" + port;
        var endpoint = "api/v1/pricing";
        pricingURL = endpoint + "/id={id}";

        String valueToEncode = userName + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(valueToEncode.getBytes());
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + base64Creds)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
