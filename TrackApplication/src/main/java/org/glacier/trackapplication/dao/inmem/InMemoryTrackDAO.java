package org.glacier.trackapplication.dao.inmem;

import org.glacier.trackapplication.dao.TrackDAO;
import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile({"inmem"})
public class InMemoryTrackDAO implements TrackDAO {

    private Map<Integer, Track> tracksMap = new HashMap<>();
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

//    @Override
//    public List<Track> getTracksByMediaType(String mediaType) {
//        boolean isValidType = EnumUtils.isValidEnumIgnoreCase(ApprovedAudioFormats.class, mediaType);
//        if (isValidType) {
//            return tracksMap.values().stream()
//                    .filter(a -> a.getAudioType() == ApprovedAudioFormats.valueOf(mediaType))
//                    .collect(Collectors.toList());
//        }
//        return null;
//    }

    @Override
    public List<Track> getTracksByYear(Integer year) {
        return tracksMap.values().stream()
                .filter(y -> y.getIssueDate().getYear() == year)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<Track> getTracksByArtistName(String artistName) {
//        return tracksMap.values()
//                .stream()
//                .filter(c -> c.getArtistList().stream()
//                        .anyMatch(t -> t.containsValue(artistName)))
//                .collect(Collectors.toList());
//    }

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



//    @PostConstruct
//    public void init() {
//        // Init list
//        Track track1 = new Track(nextId++, "Too Sweet", "Unheard", LocalDate.of(2024, 3, 22), 251, ApprovedAudioFormats.MP3);
//        track1.setArtistList(List.of(
//                Map.of(
//                        1, "Hozier"
//                )
//        ));
//
//
//        Track track2 = new Track(nextId++, "Like That", "We Don't Trust You", LocalDate.of(2024, 3, 26), 267, ApprovedAudioFormats.MP3);
//        track2.setArtistList(List.of(
//                Map.of(
//                        2, "Future",
//                        3, "Metro Boomin"
//                )
//        ));
//
//        Track track3 = new Track(nextId++, "Beautiful Things", "Fireworks & Rollerblades", LocalDate.of(2024, 1, 18), 180, ApprovedAudioFormats.MP3);
//        track3.setArtistList(List.of(
//                Map.of(
//                        4, "Benson Boone"
//                )
//        ));
//        Track track4 = new Track(nextId++, "Lost Control", "I've Tried Everything but Therapy", LocalDate.of(2023, 6, 23), 210, ApprovedAudioFormats.MP3);
//        track4.setArtistList(List.of(
//                Map.of(
//                        5, "Teddy Swims"
//                )
//        ));
//        Track track5 = new Track(nextId++, "Texas Hole 'Em", "Cowboy Carter", LocalDate.of(2024, 2, 11), 239, ApprovedAudioFormats.MP3);
//        track5.setArtistList(List.of(
//                Map.of(
//                        6, "Beyonce"
//                )
//        ));
//        tracksMap.put(track1.getId(), track1);
//        tracksMap.put(track2.getId(), track2);
//        tracksMap.put(track3.getId(), track3);
//        tracksMap.put(track4.getId(), track4);
//        tracksMap.put(track5.getId(), track5);

//    }

}


