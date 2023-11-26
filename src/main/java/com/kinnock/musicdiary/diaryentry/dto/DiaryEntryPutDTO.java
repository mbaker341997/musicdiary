package com.kinnock.musicdiary.diaryentry.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.diaryentry.entity.Rating;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryEntryPutDTO {
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate logDate;
  private final Rating rating;
  private final String review;

  @JsonCreator
  public DiaryEntryPutDTO(
      LocalDate logDate,
      Rating rating,
      String review
  ) {
    this.logDate = logDate;
    this.rating = rating;
    this.review = review;
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
