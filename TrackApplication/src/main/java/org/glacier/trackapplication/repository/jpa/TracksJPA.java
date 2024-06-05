package org.glacier.trackapplication.repository.jpa;

import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile({"jpa", "tc"})
public interface TracksJPA extends JpaRepository<Track, Integer> {

    List<Track> findAllByMediaType(String mediaType);

    List<Track> findAllByRegisterDateYear(Integer year);

    List<Track> findAllByArtistName(String name);

    List<Track> findAllByDurationSec(Integer duration);

    List<Track> findAllByDurationSecBetween(Integer duration1, Integer duration2);
}
