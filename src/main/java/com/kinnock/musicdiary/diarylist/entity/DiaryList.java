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
import java.util.List;

@Entity
@Table
public class DiaryList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "diary_user_id", nullable = false)
  private DiaryUser diaryUser;

  @OneToMany(mappedBy = "diaryList")
  private List<DiaryListEntry> diaryListEntries;

  public DiaryList() {

  }

  public DiaryList(
      DiaryUser diaryUser,
      List<DiaryListEntry> diaryListEntries,
      String title,
      String description
  ) {
    this.diaryUser = diaryUser;
    this.diaryListEntries = diaryListEntries;
    this.title = title;
    this.description = description;
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

  public List<DiaryListEntry> getDiaryListEntries() {
    return diaryListEntries;
  }

  public void setDiaryListEntries(List<DiaryListEntry> diaryListEntries) {
    this.diaryListEntries = diaryListEntries;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
