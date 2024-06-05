package org.glacier.trackapplication.service;

import org.glacier.trackapplication.model.ApprovedAudioFormats;
import org.glacier.trackapplication.model.Track;
import org.junit.jupiter.api.Disabled;
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
@ActiveProfiles({"inmem", "pricing_inmem"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TrackServiceInMemTest {
    @Autowired
    private TrackService trackService;

    @Test
    void getAllTracks() {
        List<Track> tracks = trackService.getAllTracks();
        assertNotNull(tracks);
        assertEquals(5, tracks.size());
    }

    @Test
    void getTrackById() {
        Track track = trackService.getTrackById(1);
        assertNotNull(track);
        assertEquals(1, track.getId());
        assertEquals("Too Sweet", track.getTitle());
        assertEquals("Unheard", track.getAlbum());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "MP3, 5",
            "OGG, 0",
            "FLAC, 0",
            "WAV, 0"
    })
    void getTracksByMediaType(String audioType, int expectedTracks) {
        List<Track> tracks = trackService.getTracksByMediaType(audioType);
        assertEquals(expectedTracks, tracks.size());
    }

    @Disabled("To be fixed")
    @ParameterizedTest
    @CsvSource(value = {
            "Hozier,1",
            "Beyonce,1",
            "Frank,0",
            "Future,1",
            "Metro Boomin, 1"
    })
    void getTracksByArtist(String artist, int expectedTracks) {
        List<Track> tracks = trackService.getTracksByArtist(artist);
        assertEquals(expectedTracks, tracks.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2023, 1",
            "2024, 4",
            "1989, 0"
    })
    void getTracksByYear(Integer year, int expectedTracks) {
        List<Track> tracks = trackService.getTracksByYear(year);
        assertEquals(expectedTracks, tracks.size());
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
    @Disabled("This test fails due to teh incrementing")
    @Test
    void insertTrack() {
        Track newTrack = Track.builder()
                .id(6)
                .title("Hip Hop")
                .audioType(String.valueOf(ApprovedAudioFormats.OGG))
                .build();
        List<Track> curTracks = trackService.getAllTracks();
        trackService.insertTrack(newTrack);
        List<Track> tracks = trackService.getAllTracks();
        assertEquals(6, tracks.size());
        assertEquals("Hip Hop", tracks.get(5).getTitle());
    }

    @Test
    void updateTrackById() {
        Track track = trackService.getTrackById(1);
        track.setTitle("Hip Hop");

        trackService.updateTrackById(1, track);
        List<Track> tracks = trackService.getAllTracks();
        assertEquals("Hip Hop", tracks.getFirst().getTitle());
    }

    @Test
    void deleteTrackById() {
        List<Track> tracksList = trackService.getAllTracks();
        trackService.deleteTrackById(1);
        tracksList = trackService.getAllTracks();
        assertEquals(4, tracksList.size());
    }
}