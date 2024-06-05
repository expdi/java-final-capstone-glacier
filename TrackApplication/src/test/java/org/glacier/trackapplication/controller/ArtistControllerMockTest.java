package org.glacier.trackapplication.controller;

import org.glacier.trackapplication.model.Artist;
import org.glacier.trackapplication.service.ArtistService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ArtistControllerMockTest {
    @InjectMocks
    private ArtistController artistController;

    @Mock
    private ArtistService artistService;

    Artist artist1 = Artist.builder().id(1).name("Hozier").dateOfBirth(LocalDate.of(1990,3,17)).build();
    Artist artist2 = Artist.builder().id(2).name("Future").dateOfBirth(LocalDate.of(1983, 11,20)).build();
    List<Artist> artistsList = List.of(
        artist1, artist2
    );


    @Test
    void getAllArtists() {
        when(artistService.getAllArtists()).thenReturn(artistsList);
        ResponseEntity<?> response = artistController.getAllArtists();
        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertTrue(response.getBody() instanceof List);
        assertEquals(2, ((List<?>) response.getBody()).size());
    }

    @Test
    void getAllArtistEmpty(){
        when(artistService.getAllArtists()).thenReturn(List.of());
        ResponseEntity<?> response = artistController.getAllArtists();
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getArtistById() {
        when(artistService.getArtistById(1)).thenReturn(Optional.ofNullable(artistsList.get(0)));
        ResponseEntity<?> response = artistController.getArtistById(1);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getArtistByInvalidId(){
        when(artistService.getArtistById(1)).thenReturn(Optional.empty());
        ResponseEntity<?> response = artistController.getArtistById(1);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void getArtistByName() {
        when(artistService.getArtistByName("Hozier")).thenReturn(Optional.ofNullable(artistsList.get(0)));
        ResponseEntity<?> response = artistController.getArtistByName("Hozier");
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getArtistByInvalidName(){
        when(artistService.getArtistByName("Edwin")).thenReturn(Optional.empty());
        ResponseEntity<?> response = artistController.getArtistByName("Edwin");
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void addArtist() {
        Artist newArtist = Artist.builder()
                .name("Edwin")
                .build();
        when(artistService.createArtist(newArtist)).thenReturn(3);
        ResponseEntity<?> response = artistController.addArtist(newArtist);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(3, response.getBody());
    }

    @Test
    void updateArtistById() {
        Artist artist = artistsList.get(0);
        artist.setName("New Name");

        when(artistService.updateArtist(artist.getId(), artist)).thenReturn(true);

        ResponseEntity<?> response = artistController.updateArtist(1, artist);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void updateArtistByInvalidId(){
        when(artistService.getArtistById(100)).thenReturn(Optional.empty());
        ResponseEntity<?> response = artistController.getArtistById(100);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void deleteArtistById() {
        when(artistService.getArtistById(1)).thenReturn(Optional.ofNullable(artistsList.get(0)));

        ResponseEntity<?> response = artistController.deleteArtist(1);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void deleteArtistByInvalidId(){
        when(artistService.getArtistById(100)).thenReturn(Optional.empty());
        ResponseEntity<?> response = artistController.deleteArtist(100);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    //ToDO: tO FINISH
    @Test
    @Disabled("To be updated")
    void testGetArtistByTrackId(){
//        when(artistService.getArtistById(1)).thenReturn(Optional.ofNullable(artistsList.get(0)));
//        when(artistService.getAllSongsByArtistId(1)).thenReturn((List<Track>) List.of(Map.of(1,"Track 1", 2,"track2")));
//
//        ResponseEntity<?> response = artistController.getArtistByTrackId(1);
//        assertTrue(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    void testGetArtistByTrackIdInvalidId(){
        when(artistService.getArtistById(100)).thenReturn(Optional.empty());
        ResponseEntity<?> response = artistController.getArtistByTrackId(100);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    @Disabled("To be updated")
    void testGetArtistByTrackIdInvalidIdNoContent(){
//        when(artistService.getArtistById(1)).thenReturn(Optional.ofNullable(artistsList.get(0)));
//        when(artistService.getAllSongsByArtistId(1)).thenReturn(List.of(Map.of()));
//        ResponseEntity<?> response = artistController.getArtistByTrackId(1);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}