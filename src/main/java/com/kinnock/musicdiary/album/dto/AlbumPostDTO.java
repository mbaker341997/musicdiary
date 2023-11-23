package com.kinnock.musicdiary.album.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumPostDTO {
  @NotEmpty
  private final Long submittedById;
  @NotEmpty
  private final List<Long> artistIds;
  @NotEmpty
  private final String title;
  private final String genre;
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate releaseDate;
  private final String coverArtUrl;

  @JsonCreator
  public AlbumPostDTO(
      Long submittedById,
      List<Long> artistIds,
      String title,
      String genre,
      LocalDate releaseDate,
      String coverArtUrl
  ) {
    this.submittedById = submittedById;
    this.artistIds = artistIds;
    this.title = title;
    this.genre = genre;
    this.releaseDate = releaseDate;
    this.coverArtUrl = coverArtUrl;
  }

  public Long getSubmittedById() {
    return submittedById;
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
