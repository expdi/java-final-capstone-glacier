package org.edwinsoto.trackapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.edwinsoto.trackapplication.model.Artist;
import org.edwinsoto.trackapplication.model.Track;
import org.edwinsoto.trackapplication.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/track")
public class TrackController {

    private final TrackService service;

    @Autowired
    public TrackController(TrackService service) {
        this.service = service;
    }


    // GET Methods
    @Operation(summary = "Get All Tracks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found All Tracks", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
    })
    @GetMapping("")
    public ResponseEntity<?> getAllTracks() {
        List<Track> allTracks = service.getAllTracks();
        if (allTracks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(allTracks);
    }

    @Operation(summary = "Get Track by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found Artist By ID", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "400", description= "Invalid artist ID", content= @Content),
            @ApiResponse(responseCode = "404", description="Artist ID not found", content= @Content)
    })
    @GetMapping("/id={id}")
    public ResponseEntity<Track> getTrackById(@PathVariable int id) {
        try {
            Track track = service.getTrackById(id);
            return ResponseEntity.ok().body(track);
        } catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get tracks by mediaType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found tracks by Media Type", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "204", description= "Found no tracks by Media Type", content= @Content),
            @ApiResponse(responseCode = "404", description="Media type not found", content= @Content)
    })
    @GetMapping("/media={mediaType}")
    public ResponseEntity<?> getTracksByMediaType(@PathVariable String mediaType) {
        List<Track> tracks = service.getTracksByMediaType(mediaType);
        if (tracks == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(tracks);

    }

    @Operation(summary = "Get tracks by year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found tracks by year", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "204", description= "Found no tracks by year", content= @Content),
            @ApiResponse(responseCode = "404", description="year not found", content= @Content)
    })
    @GetMapping("/year={year}")
    public ResponseEntity<?> getTracksByYear(@PathVariable Integer year) {
        List<Track> tracks = service.getTracksByYear(year);
        if (tracks == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(tracks);
    }

    @Operation(summary = "Get tracks by artist name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found tracks by artist name", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "204", description= "Found no tracks by artist name", content= @Content),
//            @ApiResponse(responseCode = "404", description=" not found", content= @Content)
    })
    @GetMapping("/artist={artistName}")
    public ResponseEntity<?> getTracksByArtist(@PathVariable String artistName) {

        List<Track> tracks = service.getTracksByArtist(artistName);
        if (tracks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(tracks);
    }

    @Operation(summary = "Get tracks by strategy and duration in seconds. Strategy = [\"LESS_THAN\", \"GREATER_THAN\", \"EQUAL\"")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found tracks by strategy and duration in seconds", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "204", description= "Found no tracks strategy and duration", content= @Content),
//            @ApiResponse(responseCode = "404", description="year not found", content= @Content)
    })
    @GetMapping("/strategy={strategy}/duration={duration}")
    public ResponseEntity<?> getTracksByStrategyAndDuration(@PathVariable String strategy, @PathVariable int duration) {
        List<Track> tracksList = service.getTracksByDuration(strategy, duration);
        return ResponseEntity.ok().body(tracksList);
    }

    @Operation(summary = "Get tracks between two durations in seconds.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Found tracks by duration range", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "204", description= "Found no tracks by duration", content= @Content),
//            @ApiResponse(responseCode = "404", description="year not found", content= @Content)
    })
    @GetMapping("/duration1={duration1}/duration2={duration2}")
    public ResponseEntity<?> getTracksBetweenDuration(@PathVariable int duration1, @PathVariable int duration2) {
        List<Track> trackList = service.getTracksByDuration(duration1, duration2);
        return ResponseEntity.ok().body(trackList);
    }


    // POST Methods
    @Operation(summary = "Add new track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Added track", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "404", description= "Added track fails", content= @Content),
    })
    @PostMapping()
    public ResponseEntity<?> addTrack(@RequestBody @Valid Track track) {
        Integer id = service.insertTrack(track);
        return ResponseEntity.ok().body(id);
    }


    // PUT Methods
    @Operation(summary = "Update existing artist by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Updated artist", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "404", description="Updated artist fails", content= @Content)
    })
    @PutMapping("/id={id}")
    public ResponseEntity<?> updateTrackById(@PathVariable Integer id, @RequestBody @Valid Track track) {
        try {
            Track validTrack = service.getTrackById(id);
            Boolean isUpdated = service.updateTrackById(id, track);
            return ResponseEntity.noContent().build();

        } catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }

    }


    // DELETE Methods
    @Operation(summary = "Delete track by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description="Added track No Content", content= @Content)
    })
    @DeleteMapping("/id={id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteTrackById(@PathVariable int id) {
        if(service.getTrackById(id) == null) {
            return ResponseEntity.noContent().build();
        }
        service.deleteTrackById(id);
        return ResponseEntity.noContent().build();
    }


}
