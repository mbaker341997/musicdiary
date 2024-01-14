package com.kinnock.musicdiary.diarylist.entity;

import com.kinnock.musicdiary.loggable.entity.Loggable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class DiaryListEntry {
  // TODO: refactor to a combo key of id-diaryList-loggable?
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "diary_list_id", nullable = false)
  private DiaryList diaryList;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loggable_id", nullable = false)
  private Loggable loggable;

  // TODO: ordering
  @NotNull
  private Integer listIndex;

  private String note;

  public DiaryListEntry() {

  }

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
