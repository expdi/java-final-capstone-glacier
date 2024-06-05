package org.glacier.trackapplication.repository.inmem;

import jakarta.annotation.PostConstruct;
import org.glacier.trackapplication.repository.ArtistDAO;
import org.glacier.trackapplication.model.Artist;
import org.glacier.trackapplication.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("inmem")
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
    public Optional<Artist> getArtistById(Integer id) {
        return Optional.ofNullable(artistsMap.get(id));
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        return Optional.ofNullable(artistsMap.values().stream().filter(artist -> artist.getName().equals(name)).findFirst().orElse(null));
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
    public void init() {

        Track track1 = Track.builder().id(1).title("Too Sweet").album("Unheard").issueDate(LocalDate.of(2024,3,22)).durationSec(251).audioType("MP3").build();
        Track track2 = Track.builder().id(2).title("Like That").album("We Don't Trust You").issueDate(LocalDate.of(2024,3,26)).durationSec(267).audioType("MP3").build();
        Track track3 = Track.builder().id(3).title("Beautiful Things").album("Fireworks & Rollerblades").issueDate(LocalDate.of(2024,1,18)).durationSec(180).audioType("MP3").build();
        Track track4 = Track.builder().id(4).title("Lost Control").album("I've Tried Everything but Therapy").issueDate(LocalDate.of(2023,6,23)).durationSec(210).audioType("MP3").build();
        Track track5 = Track.builder().id(5).title("Texas Hole 'Em").album("Cowboy Carter").issueDate(LocalDate.of(2024,2,11)).durationSec(239).audioType("MP3").build();



        Artist artist1 = Artist.builder().id(nextId++).name("Hozier").dateOfBirth(LocalDate.of(1990,3, 17)).registerDate(LocalDate.now()).modifiedDate(null).tracks(List.of(track1)).build();
        Artist artist2 = Artist.builder().id(nextId++).name("Future").dateOfBirth(LocalDate.of(1983,11, 20)).registerDate(LocalDate.now()).modifiedDate(null).tracks(List.of(track2)).build();
        Artist artist3 = Artist.builder().id(nextId++).name("Metro Boomin").dateOfBirth(LocalDate.of(1993,9, 16)).registerDate(LocalDate.now()).modifiedDate(null).tracks(List.of(track2)).build();
        Artist artist4 = Artist.builder().id(nextId++).name("Benson Boone").dateOfBirth(LocalDate.of(2002,6, 25)).registerDate(LocalDate.now()).modifiedDate(null).tracks(List.of(track3)).build();
        Artist artist5 = Artist.builder().id(nextId++).name("Teddy Swims").dateOfBirth(LocalDate.of(1992,9, 25)).registerDate(LocalDate.now()).modifiedDate(null).tracks(List.of(track4)).build();
        Artist artist6 = Artist.builder().id(nextId++).name("Beyonce").dateOfBirth(LocalDate.of(1981,9,4)).registerDate(LocalDate.now()).modifiedDate(null).tracks(List.of(track5)).build();

        artistsMap.put(artist1.getId(), artist1);
        artistsMap.put(artist2.getId(), artist2);
        artistsMap.put(artist3.getId(), artist3);
        artistsMap.put(artist4.getId(), artist4);
        artistsMap.put(artist5.getId(), artist5);
        artistsMap.put(artist6.getId(), artist6);
    }
}
