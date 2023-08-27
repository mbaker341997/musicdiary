package com.kinnock.musicdiary.concert.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.concert.entity.SetListItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetListItemDTO {
  private final Long id;

  private final Long songId;

  private final Integer length;

  private final Integer setIndex;

  @JsonCreator
  public SetListItemDTO(
      Long id,
      Long songId,
      Integer length,
      Integer setIndex
  ) {
    this.id = id;
    this.songId = songId;
    this.length = length;
    this.setIndex = setIndex;
  }

  public SetListItemDTO(SetListItem setListItem) {
    this(
        setListItem.getId(),
        setListItem.getSong().getId(),
        setListItem.getLength(),
        setListItem.getSetIndex()
    );
  }

  public Long getId() {
    return id;
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
