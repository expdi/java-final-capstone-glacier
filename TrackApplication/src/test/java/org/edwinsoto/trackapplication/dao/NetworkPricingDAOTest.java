package org.edwinsoto.trackapplication.dao;


import org.edwinsoto.trackapplication.model.Track;

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
        Track track = Track.builder().id(100).title("Terrible Song").build();

    }

}