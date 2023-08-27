package com.kinnock.musicdiary.album.entity;

import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.logitem.entity.LogItem;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Album extends LogItem {
  private String title;

  // TODO: multi-genres with some many-to-many relationship
  private String genre;

  private LocalDate releaseDate;

  private String coverArtUrl;

  public Album() {
  }

  public Album(
      DiaryUser submittedBy,
      List<Artist> artists,
      String title,
      String genre,
      LocalDate releaseDate,
      String coverArtUrl
  ) {
    super(submittedBy, artists);
    this.title = title;
    this.genre = genre;
    this.releaseDate = releaseDate;
    this.coverArtUrl = coverArtUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getCoverArtUrl() {
    return coverArtUrl;
  }

  public void setCoverArtUrl(String coverArtUrl) {
    this.coverArtUrl = coverArtUrl;
  }
}
