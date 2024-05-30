package org.edwinsoto.trackapplication.service;

import org.edwinsoto.trackapplication.model.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ArtistServiceTest {

    @Autowired
    private ArtistService artistService;

    @Test
    void createArtist() {
        List<Artist> artistsList = artistService.getAllArtists();
        Artist artist = Artist.builder().name("Test").build();
        artistService.createArtist(artist);
        artistsList = artistService.getAllArtists();
        assertEquals(7, artistsList.size());
    }

    @Test
    void getAllArtists() {
        List<Artist> artistsList = artistService.getAllArtists();
        assertEquals(6, artistsList.size());
    }

    @Test
    void getArtistById() {
        Artist artist = artistService.getArtistById(1);
        assertEquals("Hozier", artist.getName());
    }

    @Test
    void getArtistByName() {
        Artist artist = artistService.getArtistByName("Beyonce");
        assertEquals("Beyonce", artist.getName());
    }

    @Test
    void updateArtist() {
        Artist artist = artistService.getArtistById(1);
        artist.setName("HozierTwo");
        artistService.updateArtist(artist.getId(), artist);
        Artist updatedArtist = artistService.getArtistById(1);
        assertEquals("HozierTwo", updatedArtist.getName());
    }

    @Test
    void deleteArtist() {
        List<Artist> artistsList = artistService.getAllArtists();
        assertEquals(6, artistsList.size());
        artistService.deleteArtist(1);
        artistsList = artistService.getAllArtists();
        assertEquals(5, artistsList.size());
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