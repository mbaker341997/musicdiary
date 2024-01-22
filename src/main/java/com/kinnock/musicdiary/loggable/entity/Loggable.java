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
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Loggable_Type")
public abstract class Loggable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "submitted_by_id", nullable = false)
  private DiaryUser submittedBy;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name="loggable_artist")
  private Set<Artist> artists;

  private String title;

  public Loggable() {

  }

  public Loggable(DiaryUser submittedBy, Set<Artist> artists, String title) {
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

  public Set<Artist> getArtists() {
    return artists;
  }

  public void setArtists(Set<Artist> artists) {
    this.artists = artists;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
