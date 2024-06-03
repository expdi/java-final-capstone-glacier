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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    //@Test
    @Disabled("Test invalid role that doesn't exist")
    @ParameterizedTest()
    @CsvSource(value = {"user,USER", "admin, ADMIN", "notREAL,fake"})
    @WithMockUser()
    public void testGetTrackPricingWithAdminCredentialsGive200(String name, String desiredRole) throws Exception {
        var actions = mockMvc.perform(get("/api/v1/pricing/id={id}", 3).with(user(name).roles(desiredRole)));
//                .andExpect(status().is2xxSuccessful());

        System.out.println(actions);
        assertNotNull(actions);

        //assertNotNull(actions.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testGetTrackPricingWithUserCredentialsGive200() throws Exception {
        var actions = mockMvc.perform(get("/api/v1/pricing/id={id}", 3))
                .andExpect(status().is2xxSuccessful()).andReturn();

        assertNotNull(actions.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testGetTrackPricingWithAdminCredentialsGive200() throws Exception {
        var actions = mockMvc.perform(get("/api/v1/pricing/id={id}", 3))
                .andExpect(status().is2xxSuccessful()).andReturn();

        assertNotNull(actions.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser(roles = {"FAKE"})
    public void testGetTrackPricingWithInvalidRoleCredentialsGive200() throws Exception {
        var actions = mockMvc.perform(get("/api/v1/pricing/id={id}", 3))
                .andExpect(status().is2xxSuccessful()).andReturn();

        assertNotNull(actions.getResponse().getContentAsString());

    }


    @Test
    @WithMockUser(roles = {"USER"})
    public void testGetLimitWithValidateGive200() throws Exception {
        var actions = mockMvc.perform(get("/api/v1/pricing/setLimits/{lowerLimit}/{upperLimit}", 1, 10));
    }
 }
