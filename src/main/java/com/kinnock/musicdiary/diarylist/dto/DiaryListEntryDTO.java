package com.kinnock.musicdiary.diarylist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.diarylist.entity.DiaryListEntry;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryListEntryDTO {
  @NotNull
  private final Long diaryListEntryId;
  @NotNull
  private final Long diaryListId;
  @NotNull
  private final Long loggableId;
  @NotNull
  private final Integer listIndex;
  private final String note;

  @JsonCreator
  public DiaryListEntryDTO(
      Long diaryListEntryId,
      Long diaryListId,
      Long loggableId,
      Integer listIndex,
      String note
  ) {
    this.diaryListEntryId = diaryListEntryId;
    this.diaryListId = diaryListId;
    this.loggableId = loggableId;
    this.listIndex = listIndex;
    this.note = note;
  }

  public DiaryListEntryDTO(DiaryListEntry diaryListEntry) {
    this(
        diaryListEntry.getId(),
        diaryListEntry.getDiaryList().getId(),
        diaryListEntry.getLoggable().getId(),
        diaryListEntry.getListIndex(),
        diaryListEntry.getNote()
    );
  }

  public Long getDiaryListEntryId() {
    return diaryListEntryId;
  }

  public Long getDiaryListId() {
    return diaryListId;
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
