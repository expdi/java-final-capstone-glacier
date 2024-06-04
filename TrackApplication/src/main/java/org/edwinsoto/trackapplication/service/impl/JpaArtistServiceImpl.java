package org.edwinsoto.trackapplication.service.impl;

import org.edwinsoto.trackapplication.model.JpaArtist;
import org.edwinsoto.trackapplication.repository.JpaArtistRepository;
import org.edwinsoto.trackapplication.service.JpaArtistService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Service
public class JpaArtistServiceImpl implements JpaArtistService {

    private final JpaArtistRepository jpaArtistRepository;

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
        return jpaArtistRepository.existsById(id);
    }

    @Override
    public JpaArtist updateArtist(Integer id, JpaArtist jpaArtist) {
        jpaArtist.setId(id);
        jpaArtist.setModifiedDate(LocalDate.now());
        return jpaArtistRepository.findById(id).map(toBeUpdated ->{
            Optional.ofNullable(jpaArtist.getName()).ifPresent(toBeUpdated::setName);
            Optional.ofNullable(jpaArtist.getDateOfBirth()).ifPresent(toBeUpdated::setDateOfBirth);
            Optional.ofNullable(jpaArtist.getTracks()).ifPresent(toBeUpdated::setTracks);
            return jpaArtistRepository.save(toBeUpdated);
        }).orElseThrow(()-> new RuntimeException("Artist does not exist"));
    }

    @Override
    public void delete(Integer id) {
        jpaArtistRepository.deleteById(id);

    }
}
