package org.glacier.pricingapplication.controller;

import lombok.With;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PricingControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTrackPricingWithOutCredentialsGive401() throws Exception {
        MockHttpServletRequestBuilder builder = get("/api/v1/pricing/id={id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder).andExpect(status().isUnauthorized());
    }

    @ParameterizedTest()
    @CsvSource(value = {"user,USER,200", "admin, ADMIN, 200", "notREAL,fake, 403"})
    public void testPricingServiceRolesForGET(String name, String desiredRole, int expectedCode) throws Exception {
        var actions = mockMvc.perform(get("/api/v1/pricing/id={id}", 3).with(user(name).roles(desiredRole))).andReturn();
        assertEquals(expectedCode, actions.getResponse().getStatus());

    }

    @ParameterizedTest()
    @CsvSource(value = {"user,USER,403", "admin, ADMIN, 204", "notREAL,fake, 403"})
    public void testPricingServiceRolesForPUT(String name, String desiredRole, int expectedCode) throws Exception {
        var actions = mockMvc.perform(put("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 1, 10).with(user(name).roles(desiredRole))).andReturn();
        assertEquals(expectedCode, actions.getResponse().getStatus());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSetLimitsInvalidRanges() throws Exception {
        var actions = mockMvc.perform(put("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 10, 1))
                .andExpect(status().is4xxClientError());
    }
 }
