package org.glacier.trackapplication.repository.jpa;


import org.glacier.trackapplication.repository.ArtistDAO;
import org.glacier.trackapplication.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile({"jpa", "tc"})
public class ArtistJPAImpl implements ArtistDAO {

    @Autowired
    private ArtistsJPA jpaDAO;

    @Override
    public Integer createArtist(Artist artist) {
        jpaDAO.save(artist);
        return artist.getId();
    }

    @Override
    public List<Artist> getAllArtists() {
        return jpaDAO.findAll();
    }

    @Override
    public Optional<Artist> getArtistById(Integer id) {
        return jpaDAO.findById(id);
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean updateArtist(Integer id, Artist artist) {
       jpaDAO.save(artist);
       return true;
    }

    @Override
    public void insertArtist(Artist artist) {
        jpaDAO.save(artist);
    }

    @Override
    public void deleteArtist(Integer id) {
        jpaDAO.deleteById(id);
    }

    @Override
    public List<Artist> getAllSongsByArtistId(Integer id) {
        return List.of();
    }
}
