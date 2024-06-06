package org.glacier.trackapplication.service;

import jakarta.transaction.Transactional;
import org.glacier.trackapplication.model.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import testcontainer.TestContainerConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles({"jpa", "pricing_inmem"})
@SpringBootTest
public class ArtistServiceJPATest extends TestContainerConfig {

    @Autowired
    private ArtistService artistService;

    @Test
    @Transactional
    void createArtist() {
        List<Artist> artistsList = artistService.getAllArtists();
        Artist artist = Artist.builder().name("Test").build();
        artistService.createArtist(artist);
        artistsList = artistService.getAllArtists();
        assertEquals(11, artistsList.size());
    }

    @Test
    void getAllArtists() {
        List<Artist> artistsList = artistService.getAllArtists();
        assertEquals(10, artistsList.size());
    }

    @Test
    void getArtistById() {
        Optional<Artist> artist = artistService.getArtistById(1);
        assertEquals("Misha Inderwick", artist.get().getName());
    }

    @Test
    void getArtistByName() {
        Optional<Artist> artist = artistService.getArtistByName("Gregor Crummy");
        assertEquals("Gregor Crummy", artist.get().getName());
    }

    @Test
    @Transactional
    void updateArtist() {
        Optional<Artist> artist = artistService.getArtistById(1);
        artist.get().setName("Penny Bewley");
        artistService.updateArtist(artist.get().getId(), artist.orElse(null));
        Optional<Artist> updatedArtist = artistService.getArtistById(1);
        assertEquals("Penny Bewley", updatedArtist.get().getName());
    }

    @Test
    @Transactional
    void deleteArtist() {
        List<Artist> artistsList = artistService.getAllArtists();
        assertEquals(10, artistsList.size());
        artistService.deleteArtist(1);
        artistsList = artistService.getAllArtists();
        assertEquals(9, artistsList.size());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,1",
            "2,1",
            "3,1"
    })
    void getAllSongsByArtistId(Integer id, Integer expectedSize){
        assertEquals(expectedSize, artistService.getAllSongsByArtistId(id).size());
    }
}
