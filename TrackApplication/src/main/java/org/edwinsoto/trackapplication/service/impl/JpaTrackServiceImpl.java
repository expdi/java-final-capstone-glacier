package org.edwinsoto.trackapplication.service.impl;

import org.edwinsoto.trackapplication.model.JpaTrack;
import org.edwinsoto.trackapplication.repository.JpaTrackRepository;
import org.edwinsoto.trackapplication.service.JpaTrackService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
    public JpaTrack save(JpaTrack jpaAdopter) {
        return null;
    }

    @Override
    public List<JpaTrack> findAll() {
        return null;
    }

    @Override
    public Optional<JpaTrack> findOne(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean isExists(Integer id) {
        return false;
    }
}
