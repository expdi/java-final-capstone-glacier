package org.edwinsoto.trackapplication.model;

import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Track {

    private Integer id;
    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String title;
    @Size(min = 5, max= 10)
    private String album;
    @Past(message = "{validation.issueDate.Past}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;
    private Integer durationSeconds;
    private Integer price;
    ApprovedAudioFormats audioType;
    private List<Map<Integer, String>> artistList;

    @Builder
    public Track(Integer id, String title, String album, LocalDate issueDate, Integer durationSeconds, ApprovedAudioFormats audioType) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.issueDate = issueDate;
        this.durationSeconds = durationSeconds;
        this.audioType = audioType;
    }


}

