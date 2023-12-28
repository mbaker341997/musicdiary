package com.kinnock.musicdiary.diarylist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.diarylist.entity.DiaryList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryListDTO {
  private final Long diaryListId;
  private final Long diaryUserId;
  private final String title;
  private final String description;
  // TODO: add entries
  private final List<DiaryListEntryDTO> diaryListEntries;

  @JsonCreator
  public DiaryListDTO(
      Long diaryListId,
      Long diaryUserId,
      String title,
      String description,
      List<DiaryListEntryDTO> diaryListEntries
  ) {
    this.diaryListId = diaryListId;
    this.diaryUserId = diaryUserId;
    this.title = title;
    this.description = description;
    this.diaryListEntries = diaryListEntries;
  }

  public DiaryListDTO(DiaryList diaryList) {
    this(
        diaryList.getId(),
        diaryList.getDiaryUser().getId(),
        diaryList.getTitle(),
        diaryList.getDescription(),
        diaryList.getDiaryListEntries().stream().map(DiaryListEntryDTO::new).toList()
    );
  }

  public Long getDiaryListId() {
    return diaryListId;
  }

  public Long getDiaryUserId() {
    return diaryUserId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public List<DiaryListEntryDTO> getDiaryListEntries() {
    return diaryListEntries;
  }
}
