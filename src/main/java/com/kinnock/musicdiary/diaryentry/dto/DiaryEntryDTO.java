package com.kinnock.musicdiary.diaryentry.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.diaryentry.entity.DiaryEntry;
import com.kinnock.musicdiary.diaryentry.entity.Rating;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryEntryDTO {
  private final Long id;
  private final Long userId;
  private final Long loggableId;
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate logDate;
  private final Rating rating;
  private final String review;

  @JsonCreator
  public DiaryEntryDTO(
      Long id,
      Long userId,
      Long loggableId,
      LocalDate logDate,
      Rating rating,
      String review
  ) {
    this.id = id;
    this.userId = userId;
    this.loggableId = loggableId;
    this.logDate = logDate;
    this.rating = rating;
    this.review = review;
  }

  public DiaryEntryDTO(DiaryEntry diaryEntry) {
    this(
        diaryEntry.getId(),
        diaryEntry.getDiaryUser().getId(),
        diaryEntry.getLoggable().getId(),
        diaryEntry.getLogDate(),
        diaryEntry.getRating(),
        diaryEntry.getReview()
    );
  }

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getLoggableId() {
    return loggableId;
  }

  public LocalDate getLogDate() {
    return logDate;
  }

  public Rating getRating() {
    return rating;
  }

  public String getReview() {
    return review;
  }
}
