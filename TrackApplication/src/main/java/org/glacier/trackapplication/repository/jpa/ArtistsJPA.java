package org.glacier.trackapplication.repository.jpa;

import org.glacier.trackapplication.model.Artist;
import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile({"jpa", "tc"})
public interface ArtistsJPA extends JpaRepository<Artist, Integer>{

    Optional<Artist> findByName (String name);

    @Query("SELECT a.tracks from Artist a where a.id = ?1 ")
    List<Track> findTracksByArtistId(Integer id);


}
