package org.edwinsoto.trackapplication.dao;

import org.edwinsoto.trackapplication.model.Track;

import java.util.List;

public interface TrackDAO {

    // Crud Operations
    Integer createTrack(Track track);

    List<Track> getAllTracks();

    Track getTrackById(int trackId);

    List<Track> getTracksByMediaType(String mediaType);

    List<Track> getTracksByYear(Integer year);

    List<Track> getTracksByArtistName(String artistName);

    List<Track> getTracksByDuration(String strategy, Integer duration);

    List<Track> getTracksByDuration(Integer duration1, Integer duration2);

    boolean updateTrack(int trackId, Track track);

    void deleteTrackById(Integer id);


    void init();
}
