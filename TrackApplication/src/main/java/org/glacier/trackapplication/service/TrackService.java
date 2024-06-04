package org.glacier.trackapplication.service;

import org.glacier.trackapplication.dao.ArtistDAO;
import org.glacier.trackapplication.dao.TrackDAO;
import org.glacier.trackapplication.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrackService {

    private final TrackDAO repository;
    private final ArtistDAO artistDAO;
    private final PricingService pricingService;

    @Autowired
    public TrackService(TrackDAO repository , ArtistDAO artistDAO, PricingService pricingService) {
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
        Optional<Track> track = repository.getTrackById(id);
        pricingService.addRandomPrice(track.orElse(null));
        return track.orElse(null);
    }

//    public List<Track> getTracksByMediaType(String mediaType) {
//        return repository.getTracksByMediaType(mediaType);
//    }

    public List<Track> getTracksByYear(int year) {
        return repository.getTracksByYear(year);
    }
//
//    public List<Track> getTracksByArtist(String artistName) {
//        return repository.getTracksByArtistName(artistName);
//    }

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
