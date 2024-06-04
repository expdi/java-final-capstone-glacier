package org.edwinsoto.trackapplication.controller;

import org.edwinsoto.trackapplication.model.JpaArtist;
import org.edwinsoto.trackapplication.model.JpaTrack;
import org.edwinsoto.trackapplication.service.JpaArtistService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
@RestController // presentation layer
@RequestMapping("/api/v1/artists")
public class JpaArtistController {

    private JpaArtistService jpaArtistService;

    public JpaArtistController(JpaArtistService jpaArtistService) {
        this.jpaArtistService = jpaArtistService;
    }
    @PostMapping()
    public ResponseEntity<JpaArtist> createArtist(@RequestBody JpaArtist artist){
        return new ResponseEntity<>(jpaArtistService.save(artist), HttpStatus.CREATED);
    }


    @GetMapping
    public List<JpaArtist> getArtists(){
        return jpaArtistService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<JpaArtist> getArtist(@PathVariable("id") Integer id){
        Optional<JpaArtist> artist = jpaArtistService.findOne(id);
        return artist.map(jpaArtist -> new ResponseEntity<>(jpaArtist, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{id}/tracks")
    public ResponseEntity<List<JpaTrack>> getArtistTracks(@PathVariable("id") Integer id){
        Optional<JpaArtist> artist = jpaArtistService.findOne(id);
        return artist.map(jpaArtist -> new ResponseEntity<>(jpaArtist.getTracks(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JpaArtist> updateArtist( @PathVariable("id") Integer id,
                                    @RequestBody JpaArtist jpaArtist){
        if (!jpaArtistService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jpaArtist.setId(id);
        JpaArtist savedArtist = jpaArtistService.save(jpaArtist);
        return new ResponseEntity<>(savedArtist, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JpaArtist> deleteArtist(@PathVariable("id") Integer id){
        jpaArtistService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
