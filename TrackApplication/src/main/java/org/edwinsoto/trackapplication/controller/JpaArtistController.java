package org.edwinsoto.trackapplication.controller;

import org.edwinsoto.trackapplication.model.JpaArtist;
import org.edwinsoto.trackapplication.service.JpaArtistService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // TODO
    @GetMapping
    public List<JpaArtist> getArtists(){
        return null;
    }
    // TODO
    @GetMapping("/{id}")
    public ResponseEntity<JpaArtist> getArtist(@PathVariable("id") Integer id){
        return null;
    }
    // TODO
    @PutMapping("/{id}")
    public ResponseEntity<JpaArtist> updateArtist( @PathVariable("id") Integer id,
                                    @RequestBody JpaArtist jpaArtist){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JpaArtist> deleteArtist(@PathVariable("id") Integer id){
        jpaArtistService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
