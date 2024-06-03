package org.edwinsoto.trackapplication.repository;

import org.edwinsoto.trackapplication.model.JpaTrack;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("jpa")
public interface JpaTrackRepository extends JpaRepository<JpaTrack, Integer> {
}
