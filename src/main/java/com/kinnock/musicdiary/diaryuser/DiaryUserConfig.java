package com.kinnock.musicdiary.diaryuser;

import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiaryUserConfig {
  @Bean(name = "userInitializer")
  CommandLineRunner commandLineRunner(DiaryUserRepository repository) {
    return args -> {
      var kinnock =
          new DiaryUser(
              "NeilKinnock1983",
              "kinnock@fake.com",
              false, LocalDate.of(1942, Month.MARCH, 28), "I was leader of the Labour Party",
              "https://upload.wikimedia.org/wikipedia/commons/3/37/Official_portrait_of_Neil_Kinnock%2C_Member_of_the_EC_%28cropped%29.jpg"
          );

      var thatcher =
          new DiaryUser(
              "MargaretThatcher1975",
              "thatcher@fake.com",
              false, LocalDate.of(1925, Month.OCTOBER, 13), "I was leader of the Tory Party",
              "https://upload.wikimedia.org/wikipedia/commons/3/3d/Margaret_Thatcher_stock_portrait_%28cropped%29.jpg"
          );

      repository.saveAll(List.of(kinnock, thatcher));
    };
  }
}
