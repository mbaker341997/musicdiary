package com.kinnock.musicdiary.concert.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConcertPutDTO {
  private final List<Long> artistIds;
  private final String title;
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate concertDate;
  private final String venue;

  @JsonCreator
  public ConcertPutDTO(
      List<Long> artistIds,
      String title,
      LocalDate concertDate,
      String venue
  ) {
    this.artistIds = artistIds;
    this.title = title;
    this.concertDate = concertDate;
    this.venue = venue;
  }

  public List<Long> getArtistIds() {
    return artistIds;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getConcertDate() {
    return concertDate;
  }

  public String getVenue() {
    return venue;
  }
}
