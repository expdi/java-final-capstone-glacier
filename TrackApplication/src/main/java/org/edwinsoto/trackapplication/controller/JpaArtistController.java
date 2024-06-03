package org.edwinsoto.trackapplication.controller;

import org.edwinsoto.trackapplication.model.JpaArtist;
import org.edwinsoto.trackapplication.service.JpaArtistService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("jpa")
@RestController // presentation layer
@RequestMapping("/api/artists")
public class JpaArtistController {

    private JpaArtistService jpaArtistService;

    public JpaArtistController(JpaArtistService jpaArtistService) {
        this.jpaArtistService = jpaArtistService;
    }
    @PostMapping()
    public ResponseEntity<JpaArtist> createArtist(@RequestBody JpaArtist artist){
        return new ResponseEntity<>(jpaArtistService.save(artist), HttpStatus.CREATED);
    }


}
