package org.glacier.trackapplication.service;

import jakarta.transaction.Transactional;
import org.glacier.trackapplication.model.ApprovedAudioFormats;
import org.glacier.trackapplication.model.Track;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import testcontainer.TestContainerConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ActiveProfiles({"jpa", "pricing_inmem"})
@SpringBootTest
@Testcontainers(parallel = true)
public class TrackServiceJPATest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("musicdb")
            .withUsername("postgres")
            .withPassword("password")
            .withInitScript("data/data_schema.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TrackService trackService;

    @Test
    void getAllTracks() {
        List<Track> tracks = trackService.getAllTracks();
        assertNotNull(tracks);
        assertEquals(10, tracks.size());
    }

    @Test
    void getTrackById() {
        Track track = trackService.getTrackById(1);
        assertNotNull(track);
        assertEquals(1, track.getId());
        assertEquals("People, Places, Things", track.getTitle());
        assertEquals("Mostly Unfabulous Social Life of Ethan Green, The", track.getAlbum());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "MP3, 3",
            "OGG, 2",
            "FLAC, 3",
            "WAV, 2"
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
            "2024, 0",
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
    @Disabled
    void testGetTracksByDuration(String strategy, Integer duration, Integer expectedTracks) {
        List<Track> tracksList = trackService.getTracksByDuration(strategy, duration);
        assertEquals(expectedTracks, tracksList.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "500, 600, 3",
            "0, 100, 5",
            "200, 300, 0"
    })
    void testGetTracksByDurationRange(Integer durationLow, Integer durationHigh, Integer expectedTracks) {
        List<Track> tracksList = trackService.getTracksByDuration(durationLow, durationHigh);
        assertEquals(expectedTracks, tracksList.size());
    }

    @Test
    @Transactional
    void insertTrack() {
        Track newTrack = Track.builder()
                .title("Hip Hop")
                .audioType(String.valueOf(ApprovedAudioFormats.OGG))
                .build();
        int trackId = trackService.insertTrack(newTrack);
        Track track = trackService.getTrackById(trackId);
        assertNotNull(track);
        assertEquals("Hip Hop", track.getTitle());
    }

    @Test
    @Transactional
    void updateTrackById() {
        Track track = trackService.getTrackById(1);
        track.setTitle("Hip Hop");

        trackService.updateTrackById(1, track);
        Track trackUpdated = trackService.getTrackById(1);
        assertEquals("Hip Hop", trackUpdated.getTitle());
    }

    @Test
    @Transactional
    void deleteTrackById() {
        List<Track> tracksList = trackService.getAllTracks();
        trackService.deleteTrackById(1);
        tracksList = trackService.getAllTracks();
        assertEquals(9, tracksList.size());
    }
}
