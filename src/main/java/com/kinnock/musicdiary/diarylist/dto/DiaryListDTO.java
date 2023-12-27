package com.kinnock.musicdiary.diarylist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.diarylist.entity.DiaryList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryListDTO {
  private final Long diaryListId;
  private final Long diaryUserId;
  private final String title;
  private final String description;
  // TODO: add entries

  @JsonCreator
  public DiaryListDTO(Long diaryListId, Long diaryUserId, String title, String description) {
    this.diaryListId = diaryListId;
    this.diaryUserId = diaryUserId;
    this.title = title;
    this.description = description;
  }

  public DiaryListDTO(DiaryList diaryList) {
    this(
        diaryList.getId(),
        diaryList.getDiaryUser().getId(),
        diaryList.getTitle(),
        diaryList.getDescription()
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
}
