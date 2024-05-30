package org.edwinsoto.trackapplication.service;

import org.edwinsoto.trackapplication.model.Track;
import org.edwinsoto.trackapplication.repository.InMemoryArtistDAO;
import org.edwinsoto.trackapplication.repository.InMemoryTrackDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrackService {

    private final InMemoryTrackDAO repository;
    private final InMemoryArtistDAO artistDAO;
    private final PricingService pricingService;

    @Autowired
    public TrackService(InMemoryTrackDAO repository , InMemoryArtistDAO artistDAO, PricingService pricingService) {
        this.repository = repository;
        this.artistDAO = artistDAO;
        this.pricingService = pricingService;
    }

    public List<Track> getAllTracks() {
        List<Track> tracks = repository.getAllTracks();
        tracks.forEach(pricingService::addRandomPrice);
        return tracks;
    }

    public Track getTrackById(int id) {
        Track track = repository.getTrackById(id);
        pricingService.addRandomPrice(track);
        return track;
    }

    public List<Track> getTracksByMediaType(String mediaType) {
        return repository.getTracksByMediaType(mediaType);
    }

    public List<Track> getTracksByYear(int year) {
        return repository.getTracksByYear(year);
    }

    public List<Track> getTracksByArtist(String artistName) {
        return repository.getTracksByArtistName(artistName);
    }

    public List<Track> getTracksByDuration(String strategy, Integer duration){
        return repository.getTracksByDuration(strategy, duration);
    }
    public List<Track> getTracksByDuration(Integer duration1, Integer duration2){
        return repository.getTracksByDuration(duration1, duration2);
    }

    public Integer insertTrack(Track track) {
        return repository.createTrack(track);
    }

    public Boolean updateTrackById(Integer id, Track track){
        return repository.updateTrack(id, track);
    }

    public void deleteTrackById(Integer id) {
        repository.deleteTrackById(id) ;
    }


}

