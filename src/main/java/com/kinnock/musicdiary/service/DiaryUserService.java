package com.kinnock.musicdiary.service;

import com.kinnock.musicdiary.entity.DiaryUser;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DiaryUserService {

  public List<DiaryUser> getAllDiaryUsers() {
    return List.of(
        new DiaryUser(
            1L,
            "NeilKinnock1983",
            "kinnock@fake.com",
            "https://upload.wikimedia.org/wikipedia/commons/3/37/Official_portrait_of_Neil_Kinnock%2C_Member_of_the_EC_%28cropped%29.jpg",
            false,
            LocalDate.of(1776, 7, 4)
        )
    );
  }
}
