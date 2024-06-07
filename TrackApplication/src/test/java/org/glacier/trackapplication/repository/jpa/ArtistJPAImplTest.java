package org.glacier.trackapplication.repository.jpa;

import jakarta.transaction.Transactional;
import org.glacier.trackapplication.model.Artist;
import org.glacier.trackapplication.model.Track;
import org.glacier.trackapplication.repository.ArtistDAO;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import testcontainer.TestContainerConfig;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"jpa", "pricing_inmem"})
@SpringBootTest
@Testcontainers(parallel = true)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DataJpaTest
//@RunWith(SpringRunner.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArtistJPAImplTest {//extends TestContainerConfig {

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
    private ArtistDAO artistDAO;

    @Test
    @Transactional
    void createValidArtist() {
        Artist artist = Artist.builder()
                .name("Gregor Crummy")
                .build();

        assertEquals("Gregor Crummy", artist.getName());
    }

    @Test
    @Transactional
    void createInvalidArtist() {
        assertThrows(NullPointerException.class, () -> {
            Artist.builder()
                    .dateOfBirth(LocalDate.of(1989, 7, 29))
                    .build();
        });
    }
    @Test
    void getAllArtists() {
        List<Artist> artists = artistDAO.getAllArtists();
        assertNotNull(artists);
        assertTrue(!artists.isEmpty());
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
        Optional<Artist> artist = artistDAO.getArtistByName("Gregor Crummy");
        assertEquals("Gregor Crummy", artist.get().getName());
    }

    @Test
    void getArtistByInvalidName() {
        Optional<Artist> artist = artistDAO.getArtistByName("InvalidName");
        assertEquals(Optional.empty(),artist);
    }

    @Test
    @Transactional
    void updateArtist() {
        Artist artistUpdated = Artist.builder().id(1).name("Gregor Crumy").build();

        boolean isUpdated = artistDAO.updateArtist(1, artistUpdated);
        assertTrue(isUpdated);

        Optional<Artist> newArtist = artistDAO.getArtistById(1);

        assertEquals("Gregor Crumy", newArtist.get().getName());
        assertEquals(1, newArtist.get().getId());
    }

    @Test
    @Transactional
    void updateArtistInvalidId() {
        Artist artistUpdated = Artist.builder().id(100).name("Test").build();
        Optional<Artist> artist = artistDAO.getArtistById(100);

        boolean isUpdated = artistDAO.updateArtist(100, artistUpdated);
        assertFalse(isUpdated);

    }
    @Test
    @Transactional
    void insertArtist() {
        Artist newArtist = Artist.builder().name("Glacier").build();

        artistDAO.insertArtist(newArtist);
        Optional<Artist> artistCreated = artistDAO.getArtistByName("Glacier");
        assertEquals("Glacier", artistCreated.get().getName());
    }

    @Test
    @Transactional
    void deleteArtist() {
        artistDAO.deleteArtist(1);
       Optional<Artist> artistDeleted = artistDAO.getArtistById(1);
        assertEquals(Optional.empty(), artistDeleted);
    }

    @Test
    void getAllSongsByArtistId() {
        List<Track> tracks = artistDAO.getAllSongsByArtistId(5);
        assertTrue(tracks.stream().count() > 0);
    }
}