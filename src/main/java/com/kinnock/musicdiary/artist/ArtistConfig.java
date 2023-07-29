package com.kinnock.musicdiary.artist;

import com.kinnock.musicdiary.artist.entity.Artist;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtistConfig {
  @Bean(name = "artistInitializer")
  CommandLineRunner commandLineRunner(ArtistRepository repository) {
    return args -> {
      var tupac =
          new Artist(
              "Tupac Shakur",
              LocalDate.of(1971, Month.JUNE, 16),
              "https://upload.wikimedia.org/wikipedia/en/b/b5/Tupac_Amaru_Shakur2.jpg",
              "West Coast Rapper"
          );

      var biggie =
          new Artist(
              "Biggie Smalls",
              LocalDate.of(1972, Month.MAY, 21),
              "https://upload.wikimedia.org/wikipedia/en/5/51/The_Notorious_B.I.G.jpg",
              "East Coast Rapper"
          );

      repository.saveAll(List.of(tupac, biggie));
    };
  }
}
