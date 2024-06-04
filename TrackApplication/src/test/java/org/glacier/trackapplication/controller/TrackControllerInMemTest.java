package org.glacier.trackapplication.controller;

import org.glacier.trackapplication.service.TrackService;
import org.glacier.trackapplication.model.ApprovedAudioFormats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackControllerInMemTest {

    @InjectMocks
    private TrackController trackController;

    @Mock
    private TrackService trackService;

    List<OLDTrack> tracksList = List.of(
            new OLDTrack(1, "Too Sweet", "Unheard", LocalDate.of(2024,3, 22), 251, ApprovedAudioFormats.MP3),
            new OLDTrack(2, "Like That", "We Don't Trust You", LocalDate.of(2024,3, 26), 267, ApprovedAudioFormats.MP3)
    );

    @Test
    void getAllTracks() {
        when(trackService.getAllTracks()).thenReturn(tracksList);
        ResponseEntity<?> resposne = trackController.getAllTracks();
        assertTrue(resposne.getStatusCode().is2xxSuccessful());

        assertTrue(resposne.getBody() instanceof List);
        assertEquals(2, ((List<?>) resposne.getBody()).size());

    }

    @Test
    void getAllTracksEmpty(){
        when(trackService.getAllTracks()).thenReturn(List.of());
        ResponseEntity<?> resposne = trackController.getAllTracks();
        assertTrue(resposne.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getTrackById() {
        when(trackService.getTrackById(1)).thenReturn(tracksList.get(0));
        ResponseEntity<?> resposne = trackController.getTrackById(1);
        assertTrue(resposne.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getTrackByInvalidID(){
        when(trackService.getTrackById(100)).thenReturn(null);
        ResponseEntity<?> resposne = trackController.getTrackById(100);
        assertTrue(resposne.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getTracksByMediaType() {
        when(trackService.getTracksByMediaType("MP3")).thenReturn(tracksList);
        ResponseEntity<?> resposne = trackController.getTracksByMediaType("MP3");
        assertTrue(resposne.getStatusCode().is2xxSuccessful());
    }
    @Test
    void getTracksByMediaTypeNull(){
        when(trackService.getTracksByMediaType("OGG")).thenReturn(null);
        ResponseEntity<?> resposne = trackController.getTracksByMediaType("OGG");
        assertTrue(resposne.getStatusCode().is4xxClientError());
    }

    @Test
    void getTracksByYear() {
        when(trackService.getTracksByYear(2023)).thenReturn(tracksList);
        ResponseEntity<?> resposne = trackController.getTracksByYear(2023);
        assertTrue(resposne.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getTracksByYearInvalid(){
        when(trackService.getTracksByYear(2022)).thenReturn(null);
        ResponseEntity<?> resposne = trackController.getTracksByYear(2022);
        assertTrue(resposne.getStatusCode().is4xxClientError());
    }

    @Test
    void getTracksByStrategyAndDuration() {
        when(trackService.getTracksByDuration("LESS_THAN", 400)).thenReturn(tracksList);
        ResponseEntity<?> response = trackController.getTracksByStrategyAndDuration("LESS_THAN", 400);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getTracksBetweenDuration() {
        when(trackService.getTracksByDuration(100, 300)).thenReturn(tracksList);
        ResponseEntity<?> response = trackController.getTracksBetweenDuration(100, 300);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getTracksByValidArtist(){
        when(trackService.getTracksByArtist("Beyonce")).thenReturn(tracksList);
        ResponseEntity<?> response = trackController.getTracksByArtist("Beyonce");
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getTracksByInvalidArtist(){
        when(trackService.getTracksByArtist("Frank")).thenReturn(List.of());
        ResponseEntity<?> response = trackController.getTracksByArtist("Frank");
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    void addTrack() {
        OLDTrack newOLDTrack = OLDTrack.builder()
                .title("Hello Song")
                .build();
        when(trackService.insertTrack(newOLDTrack)).thenReturn(3);
        ResponseEntity<?> response = trackController.addTrack(newOLDTrack);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(3, response.getBody());
    }

    @Test
    void updateTrackById() {
        OLDTrack OLDTrack = tracksList.get(0);
        OLDTrack.setTitle("Hello Song");

        when(trackService.updateTrackById(1, OLDTrack)).thenReturn(true);

        ResponseEntity<?> response = trackController.updateTrackById(1, OLDTrack);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void updatedTrackByInvalidId(){
        OLDTrack OLDTrack = tracksList.get(0);
        OLDTrack.setId(100);
        OLDTrack.setTitle("Hello Song");
        when(trackService.getTrackById(100)).thenReturn(null);

        ResponseEntity<?> response = trackController.getTrackById(100);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void deleteTrackByValidId() {
        when(trackService.getTrackById(1)).thenReturn(tracksList.get(0));

        ResponseEntity<?> response = trackController.deleteTrackById(1);
        assertTrue(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    void deleteTrackByInvalidID(){
        when(trackService.getTrackById(100)).thenReturn(null);

        ResponseEntity<?> response = trackController.deleteTrackById(100);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}