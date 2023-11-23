package com.kinnock.musicdiary.setlistitem.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.setlistitem.entity.SetListItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetListItemDTO {
  private final Long id;
  private final Long concertId;
  private final Long songId;
  private final Integer length;
  private final Integer setIndex;

  @JsonCreator
  public SetListItemDTO(
      Long id,
      Long concertId,
      Long songId,
      Integer length,
      Integer setIndex
  ) {
    this.id = id;
    this.concertId = concertId;
    this.songId = songId;
    this.length = length;
    this.setIndex = setIndex;
  }

  public SetListItemDTO(SetListItem setListItem) {
    this(
        setListItem.getId(),
        setListItem.getConcert().getId(),
        setListItem.getSong().getId(),
        setListItem.getLength(),
        setListItem.getSetIndex()
    );
  }

  public Long getId() {
    return id;
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
