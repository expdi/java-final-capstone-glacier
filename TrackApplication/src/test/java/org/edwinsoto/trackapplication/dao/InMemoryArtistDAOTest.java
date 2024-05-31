package org.edwinsoto.trackapplication.dao;

import org.edwinsoto.trackapplication.model.Artist;
import org.edwinsoto.trackapplication.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class InMemoryArtistDAOTest {

    @Autowired
    private ArtistDAO artistDAO;
    @Autowired
    private TrackService trackService;


    @Test
    void testCreateValidArtist() {
        Artist artist = Artist.builder()
                .name("Fred Flinstone")
                .build();
        assertEquals("Fred Flinstone", artist.getName());
    }

    @Test
    void testCreateInvalidArtist() {
        Artist invalidArtist = Artist.builder()
                .dateOfBirth(LocalDate.of(1989, 7, 29))
                .build();

        assertEquals(null, invalidArtist.getName());
    }

    @Test
    void testGetAllArtists() {
        List<Artist> artists = artistDAO.getAllArtists();
        assertNotNull(artists);
        assertEquals(6, artists.size());
    }



    @Test
    void testGetArtistByValidId() {
        Artist artist = artistDAO.getArtistById(1);
        assertNotNull(artist);
        assertEquals("Hozier", artist.getName());
    }

    @Test
    void testGetArtistByInvalidId() {
        Artist artist = artistDAO.getArtistById(-1);
        assertNull(artist);
    }

    @Test
    void testGetArtistByValidName() {
        Artist artist = artistDAO.getArtistByName("Hozier");
        assertNotNull(artist);
        assertEquals("Hozier", artist.getName());
        assertEquals(LocalDate.of(1990, 3, 17), artist.getDateOfBirth());
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
        List<Artist> tracks = artistDAO.getAllSongsByArtistId(artistID);
        assertEquals(expectedTracks, tracks.size());
    }

    @Test
    void testGetArtistByInvalidName() {
        Artist artist = artistDAO.getArtistByName("Edwin");
        assertNull(artist);
    }

    @Test
    void testUpdateArtist() {
        Artist artistUpdated = new Artist(1, "Not Hozier", LocalDate.of(1990, 3, 17), List.of("gospel", "folk"));
        Artist artist = artistDAO.getArtistById(1);

        boolean isUpdated = artistDAO.updateArtist(1, artistUpdated);
        assertTrue(isUpdated);

        Artist newArtist = artistDAO.getArtistById(1);

        assertEquals("Not Hozier", newArtist.getName());
        assertEquals(1, newArtist.getId());
    }

    @Test
    void testUpdateArtistInvalidId() {
        Artist artistUpdated = new Artist(100, "Edwin", LocalDate.of(1990, 3, 17), List.of("gospel", "folk"));
        Artist artist = artistDAO.getArtistById(100);

        boolean isUpdated = artistDAO.updateArtist(100, artistUpdated);
        assertFalse(isUpdated);

    }

    @Test
    void testInsertArtist() {
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
//
//    @Test
//    void getAllTracksByArtist() {
//    }
//

}