package org.edwinsoto.trackapplication.service;

import org.edwinsoto.trackapplication.model.Artist;
import org.edwinsoto.trackapplication.model.JpaArtist;

import java.util.List;
import java.util.Optional;

public interface JpaArtistService {
    JpaArtist save(JpaArtist jpaAdopter);

    List<JpaArtist> findAll();

    Optional<JpaArtist> findOne(Integer id);

    boolean isExists(Integer id);

}
