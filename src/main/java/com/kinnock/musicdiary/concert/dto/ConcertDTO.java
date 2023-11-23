package com.kinnock.musicdiary.concert.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemDTO;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConcertDTO {
  private final Long id;
  private final Long submittedById;
  private final List<Long> artistIds;
  private final String title;
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate date;
  private final String venue;
  private final List<SetListItemDTO> setList;

  @JsonCreator
  public ConcertDTO(
      Long id,
      Long submittedById,
      List<Long> artistIds,
      String title,
      LocalDate date,
      String venue,
      List<SetListItemDTO> setList
  ) {
    this.id = id;
    this.submittedById = submittedById;
    this.artistIds = artistIds;
    this.title = title;
    this.date = date;
    this.venue = venue;
    this.setList = setList;
  }

  public ConcertDTO(Concert concert) {
    this(
        concert.getId(),
        concert.getSubmittedBy().getId(),
        concert.getArtists().stream().map(Artist::getId).toList(),
        concert.getTitle(),
        concert.getDate(),
        concert.getVenue(),
        concert.getSetListItems().stream().map(SetListItemDTO::new).toList()
    );
  }

  public Long getId() {
    return id;
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

  public List<SetListItemDTO> getSetList() {
    return setList;
  }
}
