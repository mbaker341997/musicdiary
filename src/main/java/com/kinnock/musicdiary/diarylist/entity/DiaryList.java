package com.kinnock.musicdiary.diarylist.entity;

import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table
public class DiaryList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "diary_user_id", nullable = false)
  private DiaryUser diaryUser;

  @OneToMany(mappedBy = "diarylist")
  private Set<DiaryListEntry> diaryListEntrySet;

  public DiaryList() {

  }

  public DiaryList(
      DiaryUser diaryUser,
      Set<DiaryListEntry> diaryListEntrySet
  ) {
    this.diaryUser = diaryUser;
    this.diaryListEntrySet = diaryListEntrySet;
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

  public Set<DiaryListEntry> getDiaryListEntrySet() {
    return diaryListEntrySet;
  }

  public void setDiaryListEntrySet(
      Set<DiaryListEntry> diaryListEntrySet) {
    this.diaryListEntrySet = diaryListEntrySet;
  }
}
