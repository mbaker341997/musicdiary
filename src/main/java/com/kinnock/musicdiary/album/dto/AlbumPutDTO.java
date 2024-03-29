package com.kinnock.musicdiary.album.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumPutDTO {
  private final List<Long> artistIds;
  private final String title;
  private final String genre;
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate releaseDate;
  private final String coverArtUrl;

  @JsonCreator
  public AlbumPutDTO(
      List<Long> artistIds,
      String title,
      String genre,
      LocalDate releaseDate,
      String coverArtUrl
  ) {
    this.artistIds = artistIds;
    this.title = title;
    this.genre = genre;
    this.releaseDate = releaseDate;
    this.coverArtUrl = coverArtUrl;
  }

  public List<Long> getArtistIds() {
    return artistIds;
  }

  public String getTitle() {
    return title;
  }

  public String getGenre() {
    return genre;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public String getCoverArtUrl() {
    return coverArtUrl;
  }
}
