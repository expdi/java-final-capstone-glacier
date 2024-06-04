package org.glacier.trackapplication.service;

import org.glacier.trackapplication.dao.ArtistDAO;
import org.glacier.trackapplication.model.Artist;
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

//    public Optional<Artist> getArtistByName(String name) {
//        return repository.getArtistByname(name);
//    }

    public boolean updateArtist(Integer id, Artist artist) {
        return repository.updateArtist(id, artist);
    }

    public void deleteArtist(Integer id) {
        repository.deleteArtist(id);
    }

//    public List<Map<Integer, String>> getAllSongsByArtistId(Integer id) {
//        List<Artist> artistList = repository.getAllSongsByArtistId(id);
//
//        return artistList.getFirst().getArtistTracks().stream().toList();
//
//    }


}
