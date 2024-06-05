package org.glacier.trackapplication.service;

import org.glacier.trackapplication.repository.ArtistDAO;
import org.glacier.trackapplication.model.Artist;
import org.glacier.trackapplication.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    private final ArtistDAO repository;

    @Autowired
    public ArtistService(ArtistDAO repository) {
        this.repository = repository;
    }

    public Integer createArtist(Artist artist) {
        return repository.createArtist(artist);
    }

    public List<Artist> getAllArtists() {
        return repository.getAllArtists();
    }

    public Optional<Artist> getArtistById(Integer id) {
        return repository.getArtistById(id);
    }

    public Optional<Artist> getArtistByName(String name) {
        return repository.getArtistByName(name);
    }

    public boolean updateArtist(Integer id, Artist artist) {
        return repository.updateArtist(id, artist);
    }

    public void deleteArtist(Integer id) {
        repository.deleteArtist(id);
    }

    public List<Track> getAllSongsByArtistId(int id) {
        return repository.getAllSongsByArtistId(id);
//        return artistList.getFirst().getTracks().stream().toList();
    }

}
