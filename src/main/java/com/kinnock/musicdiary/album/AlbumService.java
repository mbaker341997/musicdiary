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

  public AlbumDTO getAlbumById(Long id) {
    Album album = this.albumRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("album not found")); // TODO: some 404
    return new AlbumDTO(album);
  }

  public List<AlbumDTO> getAllAlbums() {
    return this.albumRepository.findAll().stream().map(AlbumDTO::new).toList();
  }

  public AlbumDTO updateAlbum(Long id, AlbumPutDTO albumPutDTO) {
    Album album = this.albumRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("album not found")); // TODO: some 404

    EntityUtils.updateEntityValue(
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
        l -> !Objects.isNull(l) && !l.isEmpty(),
        album::setArtists
    );

    EntityUtils.updateNonBlankStringValue(albumPutDTO::getTitle, album::setTitle);

    EntityUtils.updateNonBlankStringValue(albumPutDTO::getGenre, album::setGenre);

    EntityUtils.updateNonNullEntityValue(albumPutDTO::getReleaseDate, album::setReleaseDate);

    EntityUtils.updateNonNullEntityValue(albumPutDTO::getCoverArtUrl, album::setCoverArtUrl);

    // TODO: move towards creating albums and songs at the same time

    return new AlbumDTO(this.albumRepository.save(album));
  }

  public AlbumDTO deleteAlbum(Long id) {
    Album album = this.albumRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("album not found")); // TODO: some 404

    this.albumRepository.delete(album);

    return new AlbumDTO(album);
  }
}
