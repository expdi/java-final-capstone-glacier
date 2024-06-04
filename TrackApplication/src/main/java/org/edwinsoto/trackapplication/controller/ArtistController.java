package org.edwinsoto.trackapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.edwinsoto.trackapplication.model.Artist;
import org.edwinsoto.trackapplication.model.Track;
import org.edwinsoto.trackapplication.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Profile("inmem")
@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController {

    private final ArtistService service;

    @Autowired
    public ArtistController(ArtistService service) {
        this.service = service;
    }

    // GET Mappings
    @Operation(summary = "Get All Artists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found All Tracks", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))}),
    })
    @GetMapping("")
    public ResponseEntity<?> getAllArtists() {
        List<Artist> artists = service.getAllArtists();
        if (artists.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(artists);
    }

    @Operation(summary = "Get Artist by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found Artist By ID", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400", description= "Invalid artist ID", content= @Content),
            @ApiResponse(responseCode = "404", description="Artist ID not found", content= @Content)
    })
    @GetMapping("/id={id}")
    public ResponseEntity<?> getArtistById(@PathVariable("id") int id) {
        Artist artist = service.getArtistById(id);
        if (artist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(artist);
    }

    @Operation(summary = "Get Artist by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found Artist By Name", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404", description="Artist name not found", content= @Content)
    })
    @GetMapping("/name={name}")
    public ResponseEntity<?> getArtistByName(@PathVariable("name") String name) {
        Artist artist = service.getArtistByName(name);
        if (artist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(artist);
    }

    @Operation(summary = "Get Tracks By Artist ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found tracks by artist ID", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400", description= "Invalid artist ID", content= @Content),
            @ApiResponse(responseCode = "404", description="Found no tracks by artist ID", content= @Content)
    })
    @GetMapping("/tracks/id={id}")
    public ResponseEntity<?> getArtistByTrackId(@PathVariable("id") int id) {
        Artist artist = service.getArtistById(id);
        if (artist == null){
            return ResponseEntity.notFound().build();
        }
        List<Map<Integer,String>> tracksList = service.getAllSongsByArtistId(id);

        return ResponseEntity.ok().body(tracksList);
    }

    // POST Mappings
    @Operation(summary = "Add new Artist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Added artist", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404", description="Added Artist fails", content= @Content)
    })
    @PostMapping()
    public ResponseEntity<?> addArtist(@RequestBody Artist artist) {
        Integer id = service.createArtist(artist);
        return ResponseEntity.ok().body(id);
    }

    // PUT Mappings
    @Operation(summary = "Update existing artist by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Updated artist", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404", description="Updated artist fails", content= @Content)
    })
    @PutMapping("/id={id}")
    public ResponseEntity<?> updateArtist(@PathVariable("id") int id, @RequestBody Artist artist) {
        Boolean isUpdated = service.updateArtist(id, artist);

        return ResponseEntity.ok().build();


    }

    // DELETE Mappings
    @Operation(summary = "Delete Artist by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description="Added Artist No Content", content= @Content)
    })
    @DeleteMapping("/id={id}")
    public ResponseEntity<?> deleteArtist(@PathVariable("id") int id) {
        if(service.getArtistById(id) == null){
            return ResponseEntity.notFound().build();
        }
        service.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
