package com.kinnock.musicdiary.album;

import com.kinnock.musicdiary.album.dto.AlbumDTO;
import com.kinnock.musicdiary.album.dto.AlbumPostDTO;
import com.kinnock.musicdiary.album.dto.AlbumPutDTO;
import com.kinnock.musicdiary.album.entity.Album;
import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
  private final AlbumRepository albumRepository;
  private final DiaryUserRepository diaryUserRepository;
  private final ArtistRepository artistRepository;

  @Autowired
  public AlbumService(
      AlbumRepository albumRepository,
      DiaryUserRepository diaryUserRepository,
      ArtistRepository artistRepository
  ) {
    this.albumRepository = albumRepository;
    this.diaryUserRepository = diaryUserRepository;
    this.artistRepository = artistRepository;
  }

  public AlbumDTO createAlbum(AlbumPostDTO albumPostDTO) {
    List<Artist> artists = this.artistRepository.findAllById(albumPostDTO.getArtistIds());
    DiaryUser diaryUser = this.diaryUserRepository.findById(albumPostDTO.getSubmittedById())
        .orElseThrow(); // TODO: bad request
    Album album = new Album(
        diaryUser,
        artists,
        albumPostDTO.getTitle(),
        albumPostDTO.getGenre(),
        albumPostDTO.getReleaseDate(),
        albumPostDTO.getCoverArtUrl()
    );
    return new AlbumDTO(this.albumRepository.save(album));
  }

  // R - ALL
  public AlbumDTO getAlbumById(Long id) {
    Album album = this.albumRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("album not found")); // TODO: some 404
    return new AlbumDTO(album);
  }

  public List<AlbumDTO> getAllAlbums() {
    return albumRepository.findAll().stream().map(AlbumDTO::new).toList();
  }

  public AlbumDTO updateAlbum(Long id, AlbumPutDTO albumPutDTO) {
    Album album = this.albumRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("album not found")); // TODO: some 404

    album.setArtists(EntityUtils.resolveUpdatedFieldValue(
        () -> {
          List<Artist> artistsToUpdate = this.artistRepository.findAllById(albumPutDTO.getArtistIds());
          if (artistsToUpdate.size() != albumPutDTO.getArtistIds().size()) {
            Set<Long> existingArtistIds = artistsToUpdate
                .stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());

            List<Long> missingArtistIds = albumPutDTO.getArtistIds()
                .stream()
                .filter(artistId -> !existingArtistIds.contains(artistId))
                .toList();
            // throw some 400
            throw new IllegalStateException("invalid artist ids: " + missingArtistIds);
          }
          return artistsToUpdate;
        },
        l -> l != null && !l.isEmpty(),
        album::getArtists
    ));

    album.setTitle(EntityUtils.resolveUpdatedFieldValue(
        albumPutDTO::getTitle,
        StringUtils::isNotBlank,
        album::getTitle
    ));

    album.setGenre(EntityUtils.resolveUpdatedFieldValue(
        albumPutDTO::getGenre,
        StringUtils::isNotBlank,
        album::getGenre
    ));

    album.setReleaseDate(EntityUtils.resolveUpdatedFieldValue(
        albumPutDTO::getReleaseDate,
        Objects::nonNull,
        album::getReleaseDate
    ));

    album.setCoverArtUrl(EntityUtils.resolveUpdatedFieldValue(
        albumPutDTO::getCoverArtUrl,
        Objects::nonNull,
        album::getCoverArtUrl
    ));

    return new AlbumDTO(this.albumRepository.save(album));
  }

  // D
  public AlbumDTO deleteAlbum(Long id) {
    Album album = this.albumRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("album not found")); // TODO: some 404

    albumRepository.delete(album);

    return new AlbumDTO(album);
  }
}
