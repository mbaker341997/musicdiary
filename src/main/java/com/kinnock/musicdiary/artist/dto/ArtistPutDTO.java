package com.kinnock.musicdiary.artist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;

public class ArtistPutDTO {
  private final String name;
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate dateOfBirth;
  private final String pictureUrl;
  private final String bio;

  @JsonCreator
  public ArtistPutDTO(
      String name,
      LocalDate dateOfBirth,
      String pictureUrl,
      String bio
  ) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.pictureUrl = pictureUrl;
    this.bio = bio;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public String getBio() {
    return bio;
  }
}
