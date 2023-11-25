package com.kinnock.musicdiary.setlistitem.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.loggable.entity.Loggable;
import com.kinnock.musicdiary.setlistitem.entity.SetListItem;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetListItemDTO {
  private final Long id;
  private final Long concertId;
  private final Long songId;
  private final String title;
  private final Integer length;
  private final Integer setIndex;

  @JsonCreator
  public SetListItemDTO(
      Long id,
      Long concertId,
      Long songId,
      String title,
      Integer length,
      Integer setIndex
  ) {
    this.id = id;
    this.concertId = concertId;
    this.songId = songId;
    this.title = title;
    this.length = length;
    this.setIndex = setIndex;
  }

  public SetListItemDTO(SetListItem setListItem) {
    this(
        setListItem.getId(),
        setListItem.getConcert().getId(),
        Optional.ofNullable(setListItem.getSong()).map(Loggable::getId).orElse(null),
        setListItem.getTitle(),
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
