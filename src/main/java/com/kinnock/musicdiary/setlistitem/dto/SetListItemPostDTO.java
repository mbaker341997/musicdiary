package com.kinnock.musicdiary.setlistitem.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetListItemPostDTO {
  @NotNull
  private final Long concertId;
  private final Long songId;
  @NotNull
  private final String title;
  @NotNull
  private final Integer length;
  @NotNull
  private final Integer setIndex;

  @JsonCreator
  public SetListItemPostDTO(
      Long concertId,
      Long songId,
      String title,
      Integer length,
      Integer setIndex
  ) {
    this.concertId = concertId;
    this.songId = songId;
    this.title = title;
    this.length = length;
    this.setIndex = setIndex;
  }

  public Long getConcertId() {
    return concertId;
  }

  public Long getSongId() {
    return songId;
  }

  public String getTitle() {
    return title;
  }

  public Integer getLength() {
    return length;
  }

  public Integer getSetIndex() {
    return setIndex;
  }
}
