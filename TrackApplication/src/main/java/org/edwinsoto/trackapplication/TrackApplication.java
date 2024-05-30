package org.edwinsoto.trackapplication;

import org.edwinsoto.trackapplication.model.ApprovedAudioFormats;
import org.edwinsoto.trackapplication.model.Track;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class TrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackApplication.class, args);
    }

}


