package com.kinnock.musicdiary.artist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistDTO {
  private final Long id;

  private final String name;

  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate dateOfBirth;

  private final String pictureUrl;

  private final String bio;

  @JsonCreator
  public ArtistDTO(
      Long id,
      String name,
      LocalDate dateOfBirth,
      String pictureUrl,
      String bio
  ) {
    this.id = id;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.pictureUrl = pictureUrl;
    this.bio = bio;
  }

  public ArtistDTO(Artist artist) {
    this(
        artist.getId(),
        artist.getName(),
        artist.getDateOfBirth(),
        artist.getPictureUrl(),
        artist.getBio()
    );
  }

  public Long getId() {
    return id;
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
