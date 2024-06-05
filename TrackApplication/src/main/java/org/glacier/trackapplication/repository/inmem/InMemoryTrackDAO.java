package org.glacier.trackapplication.repository.inmem;

import jakarta.annotation.PostConstruct;
import org.glacier.trackapplication.repository.TrackDAO;
import org.glacier.trackapplication.model.Artist;
import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile({"inmem"})
public class InMemoryTrackDAO implements TrackDAO {

    private Map<Integer, Track> tracksMap = new HashMap<Integer, Track>();
    private Integer nextId = 1;


    @Override
    public Integer createTrack(Track track) {
        track.setId(nextId++);
        tracksMap.put(track.getId(), track);
        return track.getId();
    }

    @Override
    public List<Track> getAllTracks() {
        return tracksMap.values().stream().toList();

    }

    @Override
    public Optional<Track> getTrackById(int trackId) {
        return Optional.ofNullable(tracksMap.get(trackId));
    }

    @Override

    public List<Track> getTracksByMediaType(String mediaType) {
        return tracksMap.values().stream().filter(a -> Objects.equals(a.getAudioType(), mediaType)).collect(Collectors.toList());

    }

    @Override
    public List<Track> getTracksByYear(Integer year) {
        return tracksMap.values().stream()
                .filter(y -> y.getIssueDate().getYear() == year)
                .collect(Collectors.toList());
    }

    @Override
    //TODO: Need to fix as well
    public List<Track> getTracksByArtistName(String artistName) {
        return tracksMap.values()
                .stream()
                .filter(c -> c.getArtists().stream()
                        .anyMatch(t -> t.getTracks().contains(artistName)))
                .collect(Collectors.toList());

    }

    @Override
    public List<Track> getTracksByDuration(String strategy, Integer duration) {
        return switch (strategy) {
            case "LESS_THAN" ->
                    tracksMap.values().stream().filter(d -> d.getDurationSec() <= duration).collect(Collectors.toList());
            case "GREATER_THAN" ->
                    tracksMap.values().stream().filter(d -> d.getDurationSec() >= duration).collect(Collectors.toList());
            default ->
                    tracksMap.values().stream().filter(d -> Objects.equals(d.getDurationSec(), duration)).collect(Collectors.toList());
        };
    }

    @Override
    public List<Track> getTracksByDuration(Integer duration1, Integer duration2) {
        return tracksMap.values().stream()
                .filter(d -> d.getDurationSec() >= duration1 && d.getDurationSec() <= duration2)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateTrack(int trackId, Track track) {
        return tracksMap.replace(trackId, track) != null;
    }

    @Override
    public void deleteTrackById(Integer id) {
        tracksMap.remove(id);
    }



    @PostConstruct
    public void init() {
        Artist artist1 = Artist.builder().id(1).name("Hozier").build();
        Artist artist2 = Artist.builder().id(2).name("Future").build();
        Artist artist3 = Artist.builder().id(3).name("Metro Boomin").build();
        Artist artist4 = Artist.builder().id(4).name("Benson Boone").build();
        Artist artist5 = Artist.builder().id(5).name("Teddy Swim").build();
        Artist artist6 = Artist.builder().id(6).name("Beyonce").build();

        Track track1 = Track.builder().id(1).title("Too Sweet").album("Unheard").issueDate(LocalDate.of(2024,3,22)).durationSec(251).audioType("MP3").artists(List.of(artist1)).build();
        Track track2 = Track.builder().id(2).title("Like That").album("We Don't Trust You").issueDate(LocalDate.of(2024,3,26)).durationSec(267).audioType("MP3").artists(List.of(artist2,artist3)).build();
        Track track3 = Track.builder().id(3).title("Beautiful Things").album("Fireworks & Rollerblades").issueDate(LocalDate.of(2024,1,18)).durationSec(180).audioType("MP3").artists(List.of(artist4)).build();
        Track track4 = Track.builder().id(4).title("Lost Control").album("I've Tried Everything but Therapy").issueDate(LocalDate.of(2023,6,23)).durationSec(210).audioType("MP3").artists(List.of(artist5)).build();
        Track track5 = Track.builder().id(5).title("Texas Hole 'Em").album("Cowboy Carter").issueDate(LocalDate.of(2024,2,11)).durationSec(239).audioType("MP3").artists(List.of(artist6)).build();


        tracksMap.put(track1.getId(), track1);
        tracksMap.put(track2.getId(), track2);
        tracksMap.put(track3.getId(), track3);
        tracksMap.put(track4.getId(), track4);
        tracksMap.put(track5.getId(), track5);

    }

}


