package com.kinnock.musicdiary.diarylist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryListPostDTO {
  @NotEmpty
  private final Long diaryUserId;
  @NotEmpty
  private final String title;
  private final String description;

  @JsonCreator
  public DiaryListPostDTO(Long diaryUserId, String title, String description) {
    this.diaryUserId = diaryUserId;
    this.title = title;
    this.description = description;
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
