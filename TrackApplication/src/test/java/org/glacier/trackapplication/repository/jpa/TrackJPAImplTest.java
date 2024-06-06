package org.glacier.trackapplication.repository.jpa;

import jakarta.transaction.Transactional;
import org.glacier.trackapplication.model.Track;
import org.glacier.trackapplication.repository.TrackDAO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import testcontainer.TestContainerConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles({"jpa", "pricing_inmem"})
@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrackJPAImplTest extends TestContainerConfig {


    @Autowired
    private TrackDAO trackDAO;

    @Test
    @Transactional
    void testCreateValidTrack() {
        Track track = Track.builder()
                .title("People")
                .build();
        assertEquals("People", track.getTitle());
    }

    @Test
    void getAllTracks() {
        List<Track> tracksList = trackDAO.getAllTracks();
        assertNotNull(tracksList);
        assertTrue(!tracksList.isEmpty());
    }

    @Test
    void getTrackByValidId() {
        Optional<Track> track = trackDAO.getTrackById(1);
        assertNotNull(track);
        assertEquals("People, Places, Things", track.get().getTitle());
    }
    @Test
    void getTrackByInValidId() {
        Optional<Track> track = trackDAO.getTrackById(-1);
        assertEquals(Optional.empty(), track);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "MP3: 3",
            "OGG: 2",
            "FLAC: 3",
            "WAV: 2",
    }, delimiter = ':')
    void getTracksByMediaType(String mediaType, int count) {
        List<Track> tracks = trackDAO.getTracksByMediaType(mediaType);
        assertEquals(count, tracks.size());
    }

    @Test
    void getTracksByInvalidMediaType() {
        List<Track> tracks = trackDAO.getTracksByMediaType("JPG");
        assertEquals(0, tracks.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2004: 1",
            "2009: 1",
            "2024: 0"
    }, delimiter = ':')
    void getTracksByYear(int year, int count) {
        List<Track> tracks = trackDAO.getTracksByYear(year);
        assertEquals(count, tracks.size());
    }

    @Test
    void getTracksByArtistName() {
        List<Track> tracks = trackDAO.getTracksByArtistName("Gregor Crummy");
        assertTrue(!tracks.isEmpty());
    }

    @Test
    void getTracksByInvalidArtistName() {
        List<Track> tracks = trackDAO.getTracksByArtistName("Gregory Crummy");
        assertTrue(tracks.isEmpty());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "100: 300: 1",
            "20: 100: 5",
            "600: 700: 0"
    }, delimiter = ':')
    void getTracksByDurationRange(int lowerLimit, int upperLimit, int count) {
        List<Track> tracks = trackDAO.getTracksByDuration(lowerLimit, upperLimit);
        assertEquals(count, tracks.size());
    }

    @Test
    void getTracksBySpecificDuration() {
        List<Track> tracks = trackDAO.getTracksByDuration("", 519);
        assertTrue(!tracks.isEmpty());
    }

    @Test
    @Transactional
    void updateTrack() {
        Track trackForUpdate = Track.builder().id(1).title("Desert in heaven").build();

         boolean isUpdated = trackDAO.updateTrack(trackForUpdate.getId(), trackForUpdate);

         assertTrue(isUpdated);
    }

    @Test
    void trackCantUpdated() {
        Track trackForUpdate = Track.builder().id(25).title("Desert in heaven").build();

        boolean isUpdated = trackDAO.updateTrack(trackForUpdate.getId(), trackForUpdate);

        assertFalse(isUpdated);
    }

    @Test
    @Transactional
    void deleteTrackById() {
        trackDAO.deleteTrackById(10);
        Optional<Track> trackDeleted = trackDAO.getTrackById(10);

        assertEquals(Optional.empty(), trackDeleted);

    }

    @Disabled
    @Test
    void deleteTrackByInvalidId() {
        trackDAO.deleteTrackById(-1);
        Optional<Track> trackDeleted = trackDAO.getTrackById(-1);

        assertEquals(Optional.empty(), trackDeleted);

    }
}