package org.glacier.trackapplication.dao;


import org.glacier.trackapplication.dao.pricing.NetworkPricingDAO;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class NetworkPricingDAOTest {


    @MockBean
    private NetworkPricingDAO networkPricingDAO;


    @Test
    @Disabled("Not Working...")
    void testGetPrice(){
        OLDTrack OLDTrack = OLDTrack.builder().id(100).title("Terrible Song").build();

    }

}