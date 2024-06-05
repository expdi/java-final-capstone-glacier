package org.glacier.trackapplication.repository;

import org.glacier.trackapplication.model.Artist;
import org.glacier.trackapplication.model.Track;

import java.util.List;
import java.util.Optional;

public interface ArtistDAO {

    Integer createArtist(Artist artist);

    List<Artist> getAllArtists();

    Optional<Artist> getArtistById(Integer id);

    Optional<Artist> getArtistByName(String name);

    boolean updateArtist(Integer id, Artist artist);

    void insertArtist(Artist artist);

    void deleteArtist(Integer id);

    List<Track> getAllSongsByArtistId(Integer id);
}