package com.kinnock.musicdiary.diarylist.entity;

import com.kinnock.musicdiary.loggable.entity.Loggable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class DiaryListEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn
  private DiaryList diaryList;

  @NotNull
  private Loggable loggable;

  // TODO: ordering
  @NotNull
  private Integer listIndex;

  private String note;

  public DiaryListEntry(
      DiaryList diaryList,
      Loggable loggable,
      Integer listIndex,
      String note
  ) {
    this.diaryList = diaryList;
    this.loggable = loggable;
    this.listIndex = listIndex;
    this.note = note;
  }

  public Long getId() {
    return id;
  }

  public DiaryList getDiaryList() {
    return diaryList;
  }

  public void setDiaryList(DiaryList diaryList) {
    this.diaryList = diaryList;
  }

  public Loggable getLoggable() {
    return loggable;
  }

  public void setLoggable(Loggable loggable) {
    this.loggable = loggable;
  }

  public Integer getListIndex() {
    return listIndex;
  }

  public void setListIndex(Integer listIndex) {
    this.listIndex = listIndex;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
