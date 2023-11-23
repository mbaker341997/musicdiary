package com.kinnock.musicdiary.concert.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetListItemPostDTO {
  @NotNull
  private final Long songId;
  @NotNull
  private final Integer length;
  @NotNull
  private final Integer setIndex;

  @JsonCreator
  public SetListItemPostDTO(
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
