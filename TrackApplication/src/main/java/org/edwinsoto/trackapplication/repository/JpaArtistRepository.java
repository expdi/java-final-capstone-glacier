package org.edwinsoto.trackapplication.repository;

import org.edwinsoto.trackapplication.model.JpaArtist;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("jpa")
public interface JpaArtistRepository extends JpaRepository<JpaArtist, Integer> {
}
