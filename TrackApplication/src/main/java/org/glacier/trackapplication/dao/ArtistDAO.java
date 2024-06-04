package org.glacier.trackapplication.dao;

import org.glacier.trackapplication.model.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistDAO {

    Integer createArtist(Artist artist);

    List<Artist> getAllArtists();

    Optional<Artist> getArtistById(Integer id);

    //Optional<Artist> getArtistByName(String name);

    boolean updateArtist(Integer id, Artist artist);

    void insertArtist(Artist artist);

    void deleteArtist(Integer id);

    //List<Artist> getAllSongsByArtistId(Integer id);
}