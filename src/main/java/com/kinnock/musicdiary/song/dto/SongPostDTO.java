package com.kinnock.musicdiary.song.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SongPostDTO {
  @NotEmpty
  private final Long submittedById;

  @NotEmpty
  private final List<Long> artistIds;

  @NotEmpty
  private final String title;

  private final Long albumId;

  private final String lyricsUrl;

  private final Integer albumIndex;

  @JsonCreator
  public SongPostDTO(
      Long submittedById,
      List<Long> artistIds,
      String title,
      Long albumId,
      String lyricsUrl,
      Integer albumIndex
  ) {
    this.submittedById = submittedById;
    this.artistIds = artistIds;
    this.title = title;
    this.albumId = albumId;
    this.lyricsUrl = lyricsUrl;
    this.albumIndex = albumIndex;
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

  public String getLyricsUrl() {
    return lyricsUrl;
  }

  public Integer getAlbumIndex() {
    return albumIndex;
  }
}
