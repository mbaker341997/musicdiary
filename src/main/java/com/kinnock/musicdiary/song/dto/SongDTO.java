package com.kinnock.musicdiary.song.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.loggable.entity.Loggable;
import com.kinnock.musicdiary.song.entity.Song;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SongDTO {
  private final Long id;
  private final Long submittedById;
  private final List<Long> artistIds;
  private final String title;
  private final Integer length;
  private final Long albumId;
  private final String lyricsUrl;
  private final Integer albumIndex;

  @JsonCreator
  public SongDTO(
      Long id,
      Long submittedById,
      List<Long> artistIds,
      String title,
      Long albumId,
      Integer length,
      String lyricsUrl,
      Integer albumIndex
  ) {
    this.id = id;
    this.submittedById = submittedById;
    this.artistIds = artistIds;
    this.title = title;
    this.albumId = albumId;
    this.length = length;
    this.lyricsUrl = lyricsUrl;
    this.albumIndex = albumIndex;
  }

  public SongDTO(Song song) {
    this(
        song.getId(),
        song.getSubmittedBy().getId(),
        song.getArtists().stream().map(Artist::getId).toList(),
        song.getTitle(),
        Optional.ofNullable(song.getAlbum()).map(Loggable::getId).orElse(null),
        song.getLength(),
        song.getLyricsUrl(),
        song.getAlbumIndex()
    );
  }

  public Long getId() {
    return id;
  }

  public Long getSubmittedById() {
    return submittedById;
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
