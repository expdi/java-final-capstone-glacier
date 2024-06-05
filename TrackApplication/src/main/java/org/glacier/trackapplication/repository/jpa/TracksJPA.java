package org.glacier.trackapplication.repository.jpa;

import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"jpa", "tc"})
public interface TracksJPA extends JpaRepository<Track, Integer> {
}
