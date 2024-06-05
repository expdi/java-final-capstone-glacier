package org.glacier.trackapplication.repository.inmem;

import org.glacier.trackapplication.repository.TrackDAO;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("inmem")
@SpringBootTest(classes = InMemoryTrackDAO.class)
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
        Optional<Track> track = trackDAO.getTrackById(1);
        assertNotNull(track);
        assertEquals("Too Sweet", track.get().getTitle());
    }

    @Test
    void testGetTrackByInvalidId() {
        Optional<Track> track = trackDAO.getTrackById(-1);
        assertEquals(Optional.empty(), track);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "MP3: 5",
            "OGG: 0",
            "FLAC: 0",
            "WAV: 0",
    }, delimiter = ':')
    void getTracksByMediaType(String audioFile, int expectedTracks) {
        List<Track> tracks = trackDAO.getTracksByMediaType(audioFile);
        System.out.println(tracks.size());
        List<Track> tracksList = trackDAO.getTracksByMediaType(audioFile);
        assertEquals(expectedTracks, tracksList.size());
    }

    @Test
    void getTracksByMediaTypeNotValid(){
        String mediaType = "ACC";
        List<Track> tracksList = trackDAO.getTracksByMediaType(mediaType);
        assertEquals(tracksList.size(), 0);
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

    @Disabled("To be fixed")
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
        Optional<Track> track = trackDAO.getTrackById(1);
        track.get().setTitle("Bop It");
        track.get().setAlbum("Hip Hop Anonymous");

        boolean isUpdated = trackDAO.updateTrack(1, track.orElse(null));
        assertTrue(isUpdated);

        Optional<Track> updatedTrack = trackDAO.getTrackById(1);
        assertEquals("Bop It", updatedTrack.get().getTitle());
        assertEquals("Hip Hop Anonymous", updatedTrack.get().getAlbum());
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