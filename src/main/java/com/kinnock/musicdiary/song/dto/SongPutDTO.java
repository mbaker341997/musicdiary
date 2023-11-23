package com.kinnock.musicdiary.song.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;

public class SongPutDTO {
  private final List<Long> artistIds;
  private final String title;
  private final Long albumId;
  private final Integer length;
  private final String lyricsUrl;
  private final Integer albumIndex;

  @JsonCreator
  public SongPutDTO(
      List<Long> artistIds,
      String title,
      Long albumId,
      Integer length,
      String lyricsUrl,
      Integer albumIndex
  ) {
    this.artistIds = artistIds;
    this.title = title;
    this.albumId = albumId;
    this.length = length;
    this.lyricsUrl = lyricsUrl;
    this.albumIndex = albumIndex;
  }

  public List<Long> getArtistIds() {
    return artistIds;
  }

  public String getTitle() {
    return title;
  }

  public Long getAlbumId() {
    return albumId;
  }

  public Integer getLength() {
    return length;
  }

  public String getLyricsUrl() {
    return lyricsUrl;
  }

  public Integer getAlbumIndex() {
    return albumIndex;
  }
}
