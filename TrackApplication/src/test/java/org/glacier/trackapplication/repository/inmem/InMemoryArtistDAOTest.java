package org.glacier.trackapplication.repository.inmem;

import org.glacier.trackapplication.model.Track;
import org.glacier.trackapplication.repository.ArtistDAO;
import org.glacier.trackapplication.model.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("inmem")
@SpringBootTest(classes = InMemoryArtistDAO.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class InMemoryArtistDAOTest {

    @Autowired
    private ArtistDAO artistDAO;

    @Test
    void createValidArtist() {
        Artist artist = Artist.builder()
                .name("Fred Flinstone")
                .build();
        assertEquals("Fred Flinstone", artist.getName());
    }

    @Test
    void createInvalidArtist() {

        assertThrows(NullPointerException.class, () -> {
            Artist invalidArtist = Artist.builder()
                    .dateOfBirth(LocalDate.of(1989, 7, 29))
                    .build();

            assertEquals(null, invalidArtist.getName());
        });



    }
    @Test
    void getAllArtists() {
        List<Artist> artists = artistDAO.getAllArtists();
        assertNotNull(artists);
        assertEquals(6, artists.size());
    }

    @Test
    void getArtistByValidId() {
        Optional<Artist> artist = artistDAO.getArtistById(1);
        assertNotNull(artist);
        assertEquals("Hozier", artist.get().getName());
    }

    @Test
    void getArtistByInvalidId() {
        Optional<Artist> artist = artistDAO.getArtistById(-1);
        assertEquals(Optional.empty(), artist);
    }

    @Test
    void getArtistByValidName() {
        Optional<Artist> artist = artistDAO.getArtistByName("Hozier");
        assertNotNull(artist);
        assertEquals("Hozier", artist.get().getName());
        assertEquals(LocalDate.of(1990, 3, 17), artist.get().getDateOfBirth());
    }
    @Test
    void getArtistByInvalidName() {
        Optional<Artist> artist = artistDAO.getArtistByName("Edwin");
        assertEquals(Optional.empty(),artist);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,1",
            "2,1",
            "3,1",
            "4,1",
            "5,1"
    })

    void getTracksByArtist(Integer artistID, int expectedTracks) {
        List<Track> tracks = artistDAO.getAllSongsByArtistId(artistID);
        assertEquals(expectedTracks, tracks.size());
    }

    @Test
    void updateArtist() {
        Artist artistUpdated = Artist.builder().id(1).name("Not Hozier").build();

        Optional<Artist> artist = artistDAO.getArtistById(1);

        boolean isUpdated = artistDAO.updateArtist(1, artistUpdated);
        assertTrue(isUpdated);

        Optional<Artist> newArtist = artistDAO.getArtistById(1);

        assertEquals("Not Hozier", newArtist.get().getName());
        assertEquals(1, newArtist.get().getId());
    }

    @Test
    void updateArtistInvalidId() {
        Artist artistUpdated = Artist.builder().id(100).name("Edwin").build();
        Optional<Artist> artist = artistDAO.getArtistById(100);

        boolean isUpdated = artistDAO.updateArtist(100, artistUpdated);
        assertFalse(isUpdated);

    }

    @Test
    void insertArtist() {
        Artist newArtist = Artist.builder().name("Edwin").build();

        artistDAO.insertArtist(newArtist);
        List<Artist> allArtists = artistDAO.getAllArtists();
        assertEquals(7, allArtists.size());
    }

    @Test
    void deleteArtist() {
        artistDAO.deleteArtist(1);
        List<Artist> allArtists = artistDAO.getAllArtists();
        assertEquals(5, allArtists.size());
    }

//    @Test
//    void getAllSongsByArtistId() {
//    }
}