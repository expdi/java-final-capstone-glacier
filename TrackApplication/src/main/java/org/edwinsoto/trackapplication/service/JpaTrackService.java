package org.edwinsoto.trackapplication.service;

import org.edwinsoto.trackapplication.model.JpaTrack;

import java.util.List;
import java.util.Optional;

public interface JpaTrackService {
    JpaTrack save(JpaTrack jpaAdopter);

    List<JpaTrack> findAll();

    Optional<JpaTrack> findOne(Integer id);

    boolean isExists(Integer id);
}
