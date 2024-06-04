package org.glacier.pricingapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingControllerClientTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private RestClient restClient;

    @Value("${env.user.superuser}")
    private String superUser; // = env.getProperty("env.user.superuser");

    @Value("${env.user.superuserpassword}")
    private String superUserPassword ;//= System.getenv("${env.user.superuserpassword}");

    @Value("${env.user.regularuser}")
    private String regularUser;// = System.getenv("${env.user.regularuser}");

    @Value("${env.user.regularuserpassword}")
    private String regularUserPassword;// = System.getenv("${env.user.regularuserpassword}");


    @PostConstruct
    public void init() {
        var baseUrl = "http://localhost:" + port;
        var endpoint = "api/v1/pricing";

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

        ResponseEntity<Integer> response = getRestClient(this.regularUser, this.regularUserPassword).get()
                .uri("api/v1/pricing/id={id}", 1).retrieve()
                .toEntity(Integer.class);

       assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    //@CsvSource(generateCsvSourceWithCredential(this.superUser, this.superUserPassword, this.regularUser, this.regularUserPassword))
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

    //private static  String[][] generateCsvSourceWithCredential() {
    private static String[][] generateCsvSourceWithCredential(String superUser, String superUserPassword, String regularUser, String regularUserPassword) {
        return new String[][] {
                { superUser, superUserPassword },
                { regularUser, regularUserPassword}
        };
    }

//    static Stream<Arguments> generateCsvSourceWithCredential() {
//        return Stream.of(
//                Arguments.of( System.getenv("env.user.superuser"), System.getenv("env.user.superuserpassword"))
//                //,Arguments.of( "${env.user.regularuser}", "password2")
//        );
//    }
}
