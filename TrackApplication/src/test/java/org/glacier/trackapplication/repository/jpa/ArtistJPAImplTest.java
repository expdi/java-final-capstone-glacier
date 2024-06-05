package org.glacier.trackapplication.repository.jpa;

import org.glacier.trackapplication.model.Artist;
import org.glacier.trackapplication.repository.ArtistDAO;
import org.glacier.trackapplication.repository.inmem.InMemoryArtistDAO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import testcontainer.TestContainerConfig;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"jpa", "pricing_inmem"})
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArtistJPAImplTest extends TestContainerConfig {
    @Autowired
    private ArtistDAO artistDAO;
    @Test
    void createValidArtist() {
        Artist artist = Artist.builder()
                .name("Gregor Crummy")
                .build();
        assertEquals("Gregor Crummy", artist.getName());
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
        assertEquals(11, artists.size());
    }

    @Test
    void getArtistByValidId() {
        Optional<Artist> artist = artistDAO.getArtistById(2);
        assertNotNull(artist);
        assertEquals("Gregor Crummy", artist.get().getName());
    }
    @Test
    void getArtistByInvalidId() {
        Optional<Artist> artist = artistDAO.getArtistById(-1);
        assertEquals(Optional.empty(), artist);
    }
    @Test
    void getArtistByValidName() {
        Optional<Artist> artist = artistDAO.getArtistByName("Test");
        assertEquals(Optional.empty(),artist);
    }

    @Test
    void updateArtist() {
        Artist artistUpdated = Artist.builder().id(1).name("Gregor Crumy").build();

        Optional<Artist> artist = artistDAO.getArtistById(1);

        boolean isUpdated = artistDAO.updateArtist(1, artistUpdated);
        assertTrue(isUpdated);

        Optional<Artist> newArtist = artistDAO.getArtistById(1);

        assertEquals("Gregor Crumy", newArtist.get().getName());
        assertEquals(1, newArtist.get().getId());
    }

    @Test
    void updateArtistInvalidId() {
        Artist artistUpdated = Artist.builder().id(100).name("Test").build();
        Optional<Artist> artist = artistDAO.getArtistById(100);

        boolean isUpdated = artistDAO.updateArtist(100, artistUpdated);
        assertTrue(isUpdated);

    }
    @Test
    void insertArtist() {
        Artist newArtist = Artist.builder().name("Glacier").build();

        artistDAO.insertArtist(newArtist);
        List<Artist> allArtists = artistDAO.getAllArtists();
        assertEquals(11, allArtists.size());
    }

    @Test
    void deleteArtist() {
        artistDAO.deleteArtist(1);
        List<Artist> allArtists = artistDAO.getAllArtists();
        assertEquals(10, allArtists.size());
    }

    @Test
    void getAllSongsByArtistId() {
        List<Artist> artists = artistDAO.getAllSongsByArtistId(1);

    }
}