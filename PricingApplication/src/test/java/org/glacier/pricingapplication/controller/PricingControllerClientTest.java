package org.glacier.pricingapplication.controller;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(PER_CLASS)
public class PricingControllerClientTest {

    @LocalServerPort
    private int port;

    @Value("${env.user.superuser}")
    public String superUser;

    @Value("${env.user.superuserpassword}")
    private String superUserPassword;

    @Value("${env.user.regularuser}")
    private String regularUser;

    @Value("${env.user.regularuserpassword}")
    private String regularUserPassword;

    private RestClient getRestClient(String userName, String password) {
        var baseUrl = "http://localhost:" + port;
        var endpoint = "api/v1/pricing";

        String valueToEncode = userName + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(valueToEncode.getBytes());
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + base64Creds)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Test
    public void getPricingForTrackTest() throws IOException {
        ResponseEntity<Integer> response = getRestClient(this.regularUser, this.regularUserPassword).get()
                .uri("api/v1/pricing/id={id}", 1).retrieve()
                .toEntity(Integer.class);

       assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("generateCsvSourceWithCredential")
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
        ResponseEntity<?> response = getRestClient(this.superUser, this.superUserPassword).put()
                .uri("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 1, 10).retrieve()
                .toEntity(ResponseEntity.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void updateLimitForPricingByUnauthorizedUser() throws IOException {
        assertThrows(HttpClientErrorException.Forbidden.class, () -> {
            ResponseEntity<?> response = getRestClient(this.regularUser, this.regularUserPassword).put()
                    .uri("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 1, 10).retrieve()
                    .toEntity(ResponseEntity.class);;
        });
    }

    @Test
    public void updateLimitWithInvalidRangeForPricingByAuthorizedUser() throws IOException {
        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            ResponseEntity<?> response = getRestClient(this.superUser, this.superUserPassword).put()
                    .uri("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 10, 10).retrieve()
                    .toEntity(ResponseEntity.class);
        });
    }

    Stream<Arguments> generateCsvSourceWithCredential() {
        return Stream.of(
                Arguments.of( superUser, superUserPassword),
                Arguments.of( regularUser, regularUserPassword)
        );
    }
}
