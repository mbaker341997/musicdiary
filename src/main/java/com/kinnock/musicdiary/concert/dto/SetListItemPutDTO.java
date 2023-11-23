package com.kinnock.musicdiary.concert.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetListItemPutDTO {
  private final Long songId;
  private final Integer length;
  private final Integer setIndex;

  @JsonCreator
  public SetListItemPutDTO(
      Long songId,
      Integer length,
      Integer setIndex
  ) {
    this.songId = songId;
    this.length = length;
    this.setIndex = setIndex;
  }

  public Long getSongId() {
    return songId;
  }

  public Integer getLength() {
    return length;
  }

  public Integer getSetIndex() {
    return setIndex;
  }
}
