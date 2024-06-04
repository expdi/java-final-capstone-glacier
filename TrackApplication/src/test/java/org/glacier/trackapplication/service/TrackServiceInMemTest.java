package org.glacier.trackapplication.service;

import org.glacier.trackapplication.model.ApprovedAudioFormats;
import org.glacier.trackapplication.model.Track;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("inmem")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TrackServiceInMemTest {

    @Autowired
    private TrackService trackService;

    @Test
    void getAllTracks() {
        List<Track> OLDTracks = trackService.getAllTracks();
        assertNotNull(OLDTracks);
        assertEquals(5, OLDTracks.size());
    }

    @Test
    void getTrackById() {
        Track OLDTrack = trackService.getTrackById(1);
        assertNotNull(OLDTrack);
        assertEquals(1, OLDTrack.getId());
        assertEquals("Too Sweet", OLDTrack.getTitle());
        assertEquals("Unheard", OLDTrack.getAlbum());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "MP3, 5",
            "OGG, 0",
            "FLAC, 0",
            "WAV, 0"
    })
    void getTracksByMediaType(String audioType, int expectedTracks) {
        List<Track> OLDTracks = trackService.getTracksByMediaType(audioType);
        assertEquals(expectedTracks, OLDTracks.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Hozier,1",
            "Beyonce,1",
            "Frank,0",
            "Future,1",
            "Metro Boomin, 1"
    })
    void getTracksByArtist(String artist, int expectedTracks) {
        List<Track> OLDTracks = trackService.getTracksByArtist(artist);
        assertEquals(expectedTracks, OLDTracks.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2023, 1",
            "2024, 4",
            "1989, 0"
    })
    void getTracksByYear(Integer year, int expectedTracks) {
        List<Track> OLDTracks = trackService.getTracksByYear(year);
        assertEquals(expectedTracks, OLDTracks.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "LESS_THAN, 180, 1",
            "GREATER_THAN, 181, 4",
            "EQUAL,267, 1"
    })
    void testGetTracksByDuration(String strategy, Integer duration, Integer expectedTracks) {
        List<Track> tracksList = trackService.getTracksByDuration(strategy, duration);
        assertEquals(expectedTracks, tracksList.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "170, 200, 1",
            "210, 320, 4",
            "240, 252, 1"
    })
    void testGetTracksByDurationRange(Integer durationLow, Integer durationHigh, Integer expectedTracks) {
        List<Track> tracksList = trackService.getTracksByDuration(durationLow, durationHigh);
        assertEquals(expectedTracks, tracksList.size());
    }

    @Test
    void insertTrack() {
        Track newOLDTrack = Track.builder()
                .title("Hip Hop")
                .audioType(ApprovedAudioFormats.OGG)
                .build();
        trackService.insertTrack(newOLDTrack);
        List<Track> OLDTracks = trackService.getAllTracks();
        assertEquals(6, OLDTracks.size());
        assertEquals("Hip Hop", OLDTracks.get(5).getTitle());
    }

    @Test
    void updateTrackById() {
        Track OLDTrack = trackService.getTrackById(1);
        OLDTrack.setTitle("Hip Hop");

        trackService.updateTrackById(1, OLDTrack);
        List<Track> OLDTracks = trackService.getAllTracks();
        assertEquals("Hip Hop", OLDTracks.getFirst().getTitle());
    }

    @Test
    void deleteTrackById() {
        List<Track> tracksList = trackService.getAllTracks();
        trackService.deleteTrackById(1);
        tracksList = trackService.getAllTracks();
        assertEquals(4, tracksList.size());
    }
}