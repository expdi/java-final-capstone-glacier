package org.glacier.trackapplication.repository.jpa;

import org.glacier.trackapplication.repository.TrackDAO;
import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile({"jpa"})
public class TrackJPAImpl implements TrackDAO {

    private TracksJPA trackDAO;

    public TrackJPAImpl(TracksJPA trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Override
    public Integer createTrack(Track track) {
     trackDAO.save(track);
     return track.getId();
    }

    @Override
    public List<Track> getAllTracks() {
        return trackDAO.findAll();
    }

    @Override
    public Optional<Track> getTrackById(int trackId) {
        return trackDAO.findById(trackId);
    }

    @Override
    public List<Track> getTracksByMediaType(String mediaType) {
        return trackDAO.findAllByAudioType(mediaType);
    }

    @Override
    public List<Track> getTracksByYear(Integer year) {
        return trackDAO.findAllByRegisterDateYear(year);
    }

    @Override

    public List<Track> getTracksByArtistName(String artistName) {
        return trackDAO.findAllByArtistName(artistName);
    }

    @Override
    public List<Track> getTracksByDuration(String strategy, Integer duration) {
        return trackDAO.findAllByDurationSec(duration);
    }

    @Override
    public List<Track> getTracksByDuration(Integer duration1, Integer duration2) {
        return trackDAO.findAllByDurationSecBetween(duration1, duration2);
    }

    @Override
    public boolean updateTrack(int trackId, Track track) {

        if(trackDAO.existsById(trackId)) {
            trackDAO.save(track);
            return true;
        }
        return false;
    }

    @Override
    public void deleteTrackById(Integer id) {
        trackDAO.deleteById(id);
    }
}
