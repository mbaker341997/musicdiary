package com.kinnock.musicdiary.setlistitem.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetListItemPutDTO {
  private final Long concertId;
  private final Long songId;
  private final String title;
  private final Integer length;
  private final Integer setIndex;

  @JsonCreator
  public SetListItemPutDTO(
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
