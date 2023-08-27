package com.kinnock.musicdiary.album.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.album.entity.Album;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumDTO {
  private final Long id;

  private final Long submittedById;

  private final List<Long> artistIds;

  private final String title;

  private final String genre;

  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate releaseDate;

  private final String coverArtUrl;

  @JsonCreator
  public AlbumDTO(
      Long id,
      Long submittedById,
      List<Long> artistIds,
      String title,
      String genre,
      LocalDate releaseDate,
      String coverArtUrl
  ) {
    this.id = id;
    this.submittedById = submittedById;
    this.artistIds = artistIds;
    this.title = title;
    this.genre = genre;
    this.releaseDate = releaseDate;
    this.coverArtUrl = coverArtUrl;
  }

  public AlbumDTO(Album album) {
    this(
        album.getId(),
        album.getSubmittedBy().getId(),
        album.getArtists().stream().map(Artist::getId).toList(),
        album.getTitle(),
        album.getGenre(),
        album.getReleaseDate(),
        album.getCoverArtUrl()
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

  public String getGenre() {
    return genre;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public String getCoverArtUrl() {
    return coverArtUrl;
  }
}
