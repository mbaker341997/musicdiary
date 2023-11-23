package com.kinnock.musicdiary.setlistitem.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetListItemPostDTO {
  @NotNull
  private final Long concertId;
  @NotNull // TODO: at some point refactor to make it not have to be tied to a song
  private final Long songId;
  @NotNull
  private final Integer length;
  @NotNull
  private final Integer setIndex;

  @JsonCreator
  public SetListItemPostDTO(
      Long concertId,
      Long songId,
      Integer length,
      Integer setIndex
  ) {
    this.concertId = concertId;
    this.songId = songId;
    this.length = length;
    this.setIndex = setIndex;
  }

  public Long getConcertId() {
    return concertId;
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
