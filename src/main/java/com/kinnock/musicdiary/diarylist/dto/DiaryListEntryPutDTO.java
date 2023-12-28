package com.kinnock.musicdiary.diarylist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryListEntryPutDTO {
  @NotNull
  private final Long loggableId;
  @NotNull
  private final Integer listIndex;
  private final String note;

  @JsonCreator
  public DiaryListEntryPutDTO(Long loggableId, Integer listIndex, String note) {
    this.loggableId = loggableId;
    this.listIndex = listIndex;
    this.note = note;
  }

  public Long getLoggableId() {
    return loggableId;
  }

  public Integer getListIndex() {
    return listIndex;
  }

  public String getNote() {
    return note;
  }
}
