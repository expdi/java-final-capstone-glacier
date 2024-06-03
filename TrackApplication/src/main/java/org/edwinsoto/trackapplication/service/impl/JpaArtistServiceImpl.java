package org.edwinsoto.trackapplication.service.impl;

import org.edwinsoto.trackapplication.model.JpaArtist;
import org.edwinsoto.trackapplication.repository.JpaArtistRepository;
import org.edwinsoto.trackapplication.service.JpaArtistService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Profile("jpa")
@Service
public class JpaArtistServiceImpl implements JpaArtistService {

    private JpaArtistRepository jpaArtistRepository;

    public JpaArtistServiceImpl(JpaArtistRepository jpaArtistRepository) {
        this.jpaArtistRepository = jpaArtistRepository;
    }

    @Override
    public JpaArtist save(JpaArtist jpaAdopter) {
        return jpaArtistRepository.save(jpaAdopter);
    }

    @Override
    public List<JpaArtist> findAll() {
        return jpaArtistRepository.findAll();
    }

    @Override
    public Optional<JpaArtist> findOne(Integer id) {
        return jpaArtistRepository.findById(id);
    }

    @Override
    public boolean isExists(Integer id) {
        return false;
    }

    @Override
    public JpaArtist updateArtist(Integer id, JpaArtist jpaArtist) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
