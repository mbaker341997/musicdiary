package com.kinnock.musicdiary.loggable.entity;

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
@DiscriminatorColumn(name = "Loggable_Type")
public abstract class Loggable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "diary_user_id", nullable = false)
  private DiaryUser submittedBy;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name="artist_loggable")
  private List<Artist> artists;

  private String title;

  public Loggable() {

  }

  public Loggable(DiaryUser submittedBy, List<Artist> artists, String title) {
    this.submittedBy = submittedBy;
    this.artists = artists;
    this.title = title;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
