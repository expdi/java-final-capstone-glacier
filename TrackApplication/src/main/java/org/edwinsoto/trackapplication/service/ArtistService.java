package org.edwinsoto.trackapplication.service;

import org.edwinsoto.trackapplication.model.Artist;
import org.edwinsoto.trackapplication.repository.InMemoryArtistDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArtistService {

    private final InMemoryArtistDAO repository;

    @Autowired
    public ArtistService(InMemoryArtistDAO repository) {
        this.repository = repository;
    }

    public Integer createArtist(Artist artist) {
        return repository.createArtist(artist);
    }

    public List<Artist> getAllArtists() {
        return repository.getAllArtists();
    }

    public Artist getArtistById(Integer id) {
        return repository.getArtistById(id);
    }

    public Artist getArtistByName(String name) {
        return repository.getArtistByName(name);
    }

    public boolean updateArtist(Integer id, Artist artist) {
        return repository.updateArtist(id, artist);
    }

    public void deleteArtist(Integer id) {
        repository.deleteArtist(id);
    }

    public List<Map<Integer, String>> getAllSongsByArtistId(Integer id) {
        List<Artist> artistList = repository.getAllSongsByArtistId(id);
        return artistList.getFirst().getArtistTracks().stream().toList();

    }


}
