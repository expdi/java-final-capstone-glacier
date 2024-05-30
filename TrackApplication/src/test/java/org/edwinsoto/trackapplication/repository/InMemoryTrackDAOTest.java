package org.edwinsoto.trackapplication.repository;

import org.edwinsoto.trackapplication.model.ApprovedAudioFormats;
import org.edwinsoto.trackapplication.model.Artist;
import org.edwinsoto.trackapplication.model.Track;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class InMemoryTrackDAOTest {

    @Autowired
    private TrackDAO trackDAO;

    @Test
    void testCreateValidTrack() {
        Track track = Track.builder()
                .title("Bop It")
                .build();
        assertEquals("Bop It", track.getTitle());
    }

    @Test
    void testGetAllTracks() {
        List<Track> tracksList = trackDAO.getAllTracks();
        assertNotNull(tracksList);
        assertEquals(5, tracksList.size());
    }

    @Test
    void testGetTrackByValidId() {
        Track track = trackDAO.getTrackById(1);
        assertNotNull(track);
        assertEquals("Too Sweet", track.getTitle());
    }

    @Test
    void testGetTrackByInvalidId() {
        Track track = trackDAO.getTrackById(-1);
        assertNull(track);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "MP3: 5",
            "OGG: 0",
            "FLAC: 0",
            "WAV: 0",
    }, delimiter = ':')
    void getTracksByMediaType(String audioFile, int expectedTracks) {
        List<Track> tracksList = trackDAO.getTracksByMediaType(audioFile);
        assertEquals(expectedTracks, tracksList.size());
    }

    @Test
    void getTracksByMediaTypeNotValid(){
        String mediaType = "ACC";
        List<Track> tracksList = trackDAO.getTracksByMediaType(mediaType);
        assertNull(tracksList);
    }

    @ParameterizedTest
    @CsvSource(value = {"2023: 1", "2024: 4"}, delimiter = ':')
    void getTracksByValidYear(Integer year, Integer expectedTracks) {
        List<Track> tracksList = trackDAO.getTracksByYear(year);
        assertNotNull(tracksList);
        assertEquals(expectedTracks, tracksList.size());
    }


    @Test
    void getTracksByInValidYear1923() {
        List<Track> tracksList = trackDAO.getTracksByYear(1923);
        assertEquals(0, tracksList.size());
    }


    @ParameterizedTest
    @CsvSource(value = {
            "Hozier,1",
            "Beyonce,1",
            "Frank,0",
            "Future,1",
            "Metro Boomin, 1"
    })
    void getTracksByArtist(String artistName, int expectedTracks) {
        List<Track> tracks = trackDAO.getTracksByArtistName(artistName);
        assertEquals(expectedTracks, tracks.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "LESS_THAN, 180, 1",
            "GREATER_THAN, 181, 4",
            "EQUAL,267, 1"
    })
    void testGetTracksByDuration(String strategy, Integer duration, Integer expectedTracks) {
        List<Track> tracksList = trackDAO.getTracksByDuration(strategy, duration);
        assertEquals(expectedTracks, tracksList.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "170, 200, 1",
            "210, 320, 4",
            "240, 252, 1"
    })
    void testGetTracksByDurationRange(Integer durationLow, Integer durationHigh, Integer expectedTracks) {
        List<Track> tracksList = trackDAO.getTracksByDuration(durationLow, durationHigh);
        assertEquals(expectedTracks, tracksList.size());
    }

    @Test
    void updateTrack() {
        Track track = trackDAO.getTrackById(1);
        track.setTitle("Bop It");
        track.setAlbum("Hip Hop Anonymous");

        boolean isUpdated = trackDAO.updateTrack(1, track);
        assertTrue(isUpdated);

        Track updatedTrack = trackDAO.getTrackById(1);
        assertEquals("Bop It", updatedTrack.getTitle());
        assertEquals("Hip Hop Anonymous", updatedTrack.getAlbum());
    }

    @Test
    void testUpdateTrackInvalid() {
        Track track = Track.builder()
                .title("Not a real Song")
                .build();

        boolean isUpdated = trackDAO.updateTrack(100, track);
        assertFalse(isUpdated);

    }

    @Test
    void deleteTrackById() {
        List<Track> tracksList = trackDAO.getAllTracks();
        assertEquals(5, tracksList.size());
        trackDAO.deleteTrackById(1);
        tracksList = trackDAO.getAllTracks();
        assertEquals(4, tracksList.size());
    }


}