package org.edwinsoto.trackapplication.repository;

import jakarta.annotation.PostConstruct;
import org.edwinsoto.trackapplication.model.Artist;
import org.edwinsoto.trackapplication.model.Track;
import org.edwinsoto.trackapplication.service.ArtistService;
import org.edwinsoto.trackapplication.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryArtistDAO implements ArtistDAO {


    private Map<Integer, Artist> artistsMap = new HashMap<Integer, Artist>();
    private Integer nextId = 1;


    @Override
    public Integer createArtist(Artist artist){
        artist.setId(nextId++);
        artistsMap.put(artist.getId(), artist);
        return artist.getId();
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistsMap.values().stream().toList();
    }

    @Override
    public Artist getArtistById(Integer id) {
        return artistsMap.get(id);
    }

    @Override
    public Artist getArtistByName(String name) {
        return artistsMap.values().stream().filter(artist -> artist.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public boolean updateArtist(Integer id, Artist artist) {
        return artistsMap.replace(id, artist) != null;
    }

    @Override
    public void insertArtist(Artist artist) {
        artist.setId(nextId++);
        artistsMap.put(artist.getId(), artist);
    }

    @Override
    public void deleteArtist(Integer id) {
        artistsMap.remove(id);
    }

    @Override
    public List<Artist> getAllSongsByArtistId(Integer id) {
        return artistsMap.values()
                .stream()
                .filter(s -> s.getId()
                        .equals(id))
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void init(){
        Artist artist1 = new Artist(nextId++, "Hozier", LocalDate.of(1990, 3, 17), List.of("gospel", "folk"));
        artist1.setArtistTracks(List.of(
                Map.of(
                    1, "Too Sweet"
                )
        ));
        Artist artist2 = new Artist(nextId++, "Future", LocalDate.of(1983, 11, 20), List.of("Hip Hop", "Trap", "Mumble Rap"));
        artist2.setArtistTracks(List.of(
                Map.of(
                        2, "Like That"
                )
        ));
        Artist artist3 = new Artist(nextId++, "Metro Boomin", LocalDate.of(1993, 9, 16), List.of("Hip Hop", "Trap"));
        artist3.setArtistTracks(List.of(
                Map.of(
                        2, "Too Sweet"
                )
        ));
        Artist artist4 = new Artist(nextId++, "Benson Boone", LocalDate.of(2002, 6, 25), List.of("Pop Rock"));
        artist4.setArtistTracks(List.of(
                Map.of(
                        3, "Beuatiful Things"
                )
        ));
        Artist artist5 = new Artist(nextId++, "Teddy Swims", LocalDate.of(1992, 9, 25), List.of("R&B", "Soul", "Country", "Pop", "Blue-eyed Soul"));
        artist5.setArtistTracks(List.of(
                Map.of(
                        4, "Lost Control"
                )
        ));
        Artist artist6 = new Artist(nextId++, "Beyonce", LocalDate.of(1981, 9, 4), List.of("R&B", "Pop", "Hip Hop", "Afrobeats", "House", "Country"));
        artist6.setArtistTracks(List.of(
                Map.of(
                        5, "Texas Hole 'Em"
                )
        ));

        artistsMap.put(artist1.getId(), artist1);
        artistsMap.put(artist2.getId(), artist2);
        artistsMap.put(artist3.getId(), artist3);
        artistsMap.put(artist4.getId(), artist4);
        artistsMap.put(artist5.getId(), artist5);
        artistsMap.put(artist6.getId(), artist6);
    }
}
