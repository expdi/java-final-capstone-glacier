package org.glacier.trackapplication.repository.jpa;

import org.glacier.trackapplication.repository.TrackDAO;
import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile({"jpa", "tc"})
public class TrackJPAImpl implements TrackDAO {

    private TracksJPA trackDAO;

    public TrackJPAImpl(TracksJPA trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Override
    public Integer createTrack(Track track) {
     trackDAO.save(track);
     return 1;
    }

    @Override
    public List<Track> getAllTracks() {
        return List.of();
    }

    @Override
    public Optional<Track> getTrackById(int trackId) {
        return trackDAO.findById(trackId);
    }

    @Override
    //TODO: Need to be fixed
    public List<Track> getTracksByMediaType(String mediaType) {
        return List.of();
    }

    @Override
    public List<Track> getTracksByYear(Integer year) {
        return List.of();
    }

    @Override
    //Need to be fixed
    public List<Track> getTracksByArtistName(String artistName) {
        return List.of();
    }

    @Override
    public List<Track> getTracksByDuration(String strategy, Integer duration) {
        return List.of();
    }

    @Override
    public List<Track> getTracksByDuration(Integer duration1, Integer duration2) {
        return List.of();
    }

    @Override
    public boolean updateTrack(int trackId, Track track) {
        return false;
    }

    @Override
    public void deleteTrackById(Integer id) {

    }

//    @Override
//    public void init() {
//
//    }
}
