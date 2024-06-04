package org.glacier.trackapplication.dao.jpa;

import org.glacier.trackapplication.model.Artist;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"jpa", "tc"})
public interface ArtistsJPA extends JpaRepository<Artist, Integer>{}
