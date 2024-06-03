package org.edwinsoto.trackapplication.service.impl;

import org.edwinsoto.trackapplication.model.JpaTrack;
import org.edwinsoto.trackapplication.repository.JpaTrackRepository;
import org.edwinsoto.trackapplication.service.JpaTrackService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Service
public class JpaTrackServiceImpl implements JpaTrackService {

    private JpaTrackRepository jpaTrackRepository;

    public JpaTrackServiceImpl(JpaTrackRepository jpaTrackRepository) {
        this.jpaTrackRepository = jpaTrackRepository;
    }

    @Override
    public JpaTrack save(JpaTrack jpaTrack) {
        return jpaTrackRepository.save(jpaTrack);
    }

    @Override
    public List<JpaTrack> findAll() {
        return jpaTrackRepository.findAll();
    }

    @Override
    public Optional<JpaTrack> findOne(Integer id) {
        return jpaTrackRepository.findById(id);
    }

    @Override
    public boolean isExists(Integer id) {
        return jpaTrackRepository.existsById(id);
    }

    @Override
    public JpaTrack updateTrack(Integer id, JpaTrack jpaTrack) {
        jpaTrack.setId(id);
        jpaTrack.setModifiedDate(LocalDate.now());
        return jpaTrackRepository.findById(id).map(toBeUpdated ->{
            Optional.ofNullable(jpaTrack.getTitle()).ifPresent(toBeUpdated::setTitle);
            Optional.ofNullable(jpaTrack.getAlbum()).ifPresent(toBeUpdated::setAlbum);
            Optional.ofNullable(jpaTrack.getIssueDate()).ifPresent(toBeUpdated::setIssueDate);
            Optional.ofNullable(jpaTrack.getDurationSec()).ifPresent(toBeUpdated::setDurationSec);
            Optional.ofNullable(jpaTrack.getAudioType()).ifPresent(toBeUpdated::setAudioType);
            Optional.ofNullable(jpaTrack.getGenre()).ifPresent(toBeUpdated::setGenre);
            Optional.ofNullable(jpaTrack.getArtists()).ifPresent(toBeUpdated::setArtists);

            return jpaTrackRepository.save(toBeUpdated);
        }).orElseThrow(()-> new RuntimeException("Track does not exist"));
    }

    @Override
    public void delete(Integer id) {
        jpaTrackRepository.deleteById(id);
    }
}
