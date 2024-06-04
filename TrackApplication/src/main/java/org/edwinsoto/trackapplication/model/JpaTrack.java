package org.edwinsoto.trackapplication.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;


@Profile("jpa")
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="tracks")
public class JpaTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String title;

    private String album;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;

    private Integer durationSec;

    private String audioType;

    private String genre;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

    @ManyToMany(mappedBy = "tracks")
    private List<JpaArtist> artists;
}
