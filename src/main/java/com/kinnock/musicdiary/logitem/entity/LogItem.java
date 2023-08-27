package com.kinnock.musicdiary.logitem.entity;

import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "LogItem_Type")
public abstract class LogItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO: audited entities will probably cover for this when i switch to that
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "diary_user_id", nullable = false)
  private DiaryUser submittedBy;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name="artist_log_item")
  private List<Artist> artists;

  public LogItem() {

  }

  public LogItem(DiaryUser submittedBy, List<Artist> artists) {
    this.submittedBy = submittedBy;
    this.artists = artists;
  }

  public Long getId() {
    return id;
  }

  public DiaryUser getSubmittedBy() {
    return submittedBy;
  }

  public void setSubmittedBy(DiaryUser submittedBy) {
    this.submittedBy = submittedBy;
  }

  public List<Artist> getArtists() {
    return artists;
  }

  public void setArtists(List<Artist> artists) {
    this.artists = artists;
  }
}
