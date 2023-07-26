package com.kinnock.musicdiary.diaryuser;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiaryUserConfig {
  @Bean
  CommandLineRunner commandLineRunner(DiaryUserRepository repository) {
    return args -> {
      var kinnock =
          new DiaryUser(
              "NeilKinnock1983",
              "kinnock@fake.com",
              "https://upload.wikimedia.org/wikipedia/commons/3/37/Official_portrait_of_Neil_Kinnock%2C_Member_of_the_EC_%28cropped%29.jpg",
              false,
              LocalDate.of(1942, Month.MARCH, 28));

      var thatcher =
          new DiaryUser(
              "MargaretThatcher1975",
              "thatcher@fake.com",
              "https://upload.wikimedia.org/wikipedia/commons/3/3d/Margaret_Thatcher_stock_portrait_%28cropped%29.jpg",
              false,
              LocalDate.of(1925, Month.OCTOBER, 13));

      repository.saveAll(List.of(kinnock, thatcher));
    };
  }
}
