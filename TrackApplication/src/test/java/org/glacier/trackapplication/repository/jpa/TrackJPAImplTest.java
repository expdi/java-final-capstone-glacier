package org.glacier.trackapplication.repository.jpa;

import org.glacier.trackapplication.model.Track;
import org.glacier.trackapplication.repository.TrackDAO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import testcontainer.TestContainerConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"jpa", "pricing_inmem"})
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrackJPAImplTest extends TestContainerConfig {
    @Autowired
    private TrackDAO trackDAO;
    @Test
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
        assertEquals(10, tracksList.size());
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
            "MP3: 5",
            "OGG: 0",
            "FLAC: 0",
            "WAV: 0",
    }, delimiter = ':')
    void getTracksByMediaType() {

    }

    @Test
    void getTracksByYear() {
    }

    @Test
    void getTracksByArtistName() {
    }

    @Test
    void getTracksByDuration() {
    }

    @Test
    void testGetTracksByDuration() {
    }

    @Test
    void updateTrack() {
    }

    @Test
    void deleteTrackById() {
    }
}