package com.kinnock.musicdiary.concert.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConcertPostDTO {
  @NotEmpty
  private final Long submittedById;
  private final List<Long> artistIds;
  @NotEmpty
  private final String title;
  @NotEmpty
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate date;
  private final String venue;

  @JsonCreator
  public ConcertPostDTO(
      Long submittedById,
      List<Long> artistIds,
      String title,
      LocalDate date,
      String venue
  ) {
    this.submittedById = submittedById;
    this.artistIds = artistIds;
    this.title = title;
    this.date = date;
    this.venue = venue;
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

  public LocalDate getDate() {
    return date;
  }

  public String getVenue() {
    return venue;
  }
}
