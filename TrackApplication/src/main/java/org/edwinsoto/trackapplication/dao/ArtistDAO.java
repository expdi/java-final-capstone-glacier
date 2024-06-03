package org.edwinsoto.trackapplication.dao;

import org.edwinsoto.trackapplication.model.Artist;

import java.util.List;

public interface ArtistDAO {

    Integer createArtist(Artist artist);

    List<Artist> getAllArtists();

    Artist getArtistById(Integer id);

    Artist getArtistByName(String name);

    boolean updateArtist(Integer id, Artist artist);

    void insertArtist(Artist artist);

    void deleteArtist(Integer id);

    List<Artist> getAllSongsByArtistId(Integer id);
}
