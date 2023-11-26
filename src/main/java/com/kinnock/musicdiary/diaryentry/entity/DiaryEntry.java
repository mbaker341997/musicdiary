package com.kinnock.musicdiary.diaryentry.entity;

import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.loggable.entity.Loggable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table
public class DiaryEntry {
  // TODO: composite key?
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "diary_user_id", nullable = false)
  private DiaryUser diaryUser;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loggable_id", nullable = false)
  private Loggable loggable;

  private LocalDate logDate;

  @Enumerated(EnumType.ORDINAL)
  private Rating rating;

  private String review;

  public DiaryEntry() {

  }

  public DiaryEntry(
      DiaryUser diaryUser,
      Loggable loggable,
      LocalDate logDate,
      Rating rating,
      String review
  ) {
    this.diaryUser = diaryUser;
    this.loggable = loggable;
    this.logDate = logDate;
    this.rating = rating;
    this.review = review;
  }

  public Long getId() {
    return id;
  }

  public DiaryUser getDiaryUser() {
    return diaryUser;
  }

  public void setDiaryUser(DiaryUser diaryUser) {
    this.diaryUser = diaryUser;
  }

  public Loggable getLoggable() {
    return loggable;
  }

  public void setLoggable(Loggable loggable) {
    this.loggable = loggable;
  }

  public LocalDate getLogDate() {
    return logDate;
  }

  public void setLogDate(LocalDate logDate) {
    this.logDate = logDate;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }
}
