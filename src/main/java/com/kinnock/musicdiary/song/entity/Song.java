package com.kinnock.musicdiary.song.entity;

import com.kinnock.musicdiary.album.entity.Album;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.loggable.entity.Loggable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Set;

@Entity
public class Song extends Loggable {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "song_album_id")
  private Album album;

  // in seconds
  private Integer length;

  // TODO: idea is to have lyrics be in a file somewhere to reference, review this again
  private String lyricsUrl;

  private Integer albumIndex;

  public Song() {

  }

  public Song(
      DiaryUser submittedBy,
      Set<Artist> artists,
      String title,
      Integer length,
      String lyricsUrl
  ) {
    super(submittedBy, artists, title);
    this.length = length;
    this.lyricsUrl = lyricsUrl;
  }

  public Song(
      DiaryUser submittedBy,
      Set<Artist> artists,
      String title,
      Album album,
      Integer length,
      String lyricsUrl,
      Integer albumIndex
  ) {
    super(submittedBy, artists, title);
    this.album = album;
    this.length = length;
    this.lyricsUrl = lyricsUrl;
    this.albumIndex = albumIndex;
  }

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public String getLyricsUrl() {
    return lyricsUrl;
  }

  public void setLyricsUrl(String lyricsUrl) {
    this.lyricsUrl = lyricsUrl;
  }

  public Integer getAlbumIndex() {
    return albumIndex;
  }

  public void setAlbumIndex(Integer albumIndex) {
    this.albumIndex = albumIndex;
  }
}
