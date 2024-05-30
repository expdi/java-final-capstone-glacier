package org.edwinsoto.trackapplication.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Artist {
    private Integer id;
    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private List<String> genresList;
    private List<Map<Integer, String>> artistTracks;

    @Builder
    public Artist(Integer id, String name, LocalDate dateOfBirth, List<String> genres) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.genresList = genres;
    }

}
