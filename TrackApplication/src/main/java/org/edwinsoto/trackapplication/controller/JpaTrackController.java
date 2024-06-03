package org.edwinsoto.trackapplication.controller;

import org.edwinsoto.trackapplication.model.JpaTrack;
import org.edwinsoto.trackapplication.service.JpaArtistService;
import org.edwinsoto.trackapplication.service.JpaTrackService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("jpa")
@RestController // presentation layer
@RequestMapping("/api/tracks")
public class JpaTrackController {

    private JpaTrackService jpaTrackService;

    public JpaTrackController(JpaTrackService jpaTrackService) {
        this.jpaTrackService = jpaTrackService;
    }

    @PostMapping
    public ResponseEntity<JpaTrack> createTrack(@RequestBody JpaTrack track){
        return new ResponseEntity<>(jpaTrackService.save(track), HttpStatus.CREATED);
    }
}
