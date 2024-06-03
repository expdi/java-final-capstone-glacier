package org.edwinsoto.trackapplication.service.impl;

import org.edwinsoto.trackapplication.model.JpaArtist;
import org.edwinsoto.trackapplication.repository.JpaArtistRepository;
import org.edwinsoto.trackapplication.service.JpaArtistService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Service
public class JpaArtistServiceImpl implements JpaArtistService {

    private JpaArtistRepository jpaArtistRepository;

    public JpaArtistServiceImpl(JpaArtistRepository jpaArtistRepository) {
        this.jpaArtistRepository = jpaArtistRepository;
    }

    @Override
    public JpaArtist save(JpaArtist jpaAdopter) {
        return null;
    }

    @Override
    public List<JpaArtist> findAll() {
        return null;
    }

    @Override
    public Optional<JpaArtist> findOne(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean isExists(Integer id) {
        return false;
    }
}
