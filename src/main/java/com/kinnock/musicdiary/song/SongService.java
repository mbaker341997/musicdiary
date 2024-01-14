package com.kinnock.musicdiary.song;

import com.kinnock.musicdiary.album.AlbumRepository;
import com.kinnock.musicdiary.album.entity.Album;
import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.exception.ResourceDoesNotExistException;
import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import com.kinnock.musicdiary.song.dto.SongDTO;
import com.kinnock.musicdiary.song.dto.SongPostDTO;
import com.kinnock.musicdiary.song.dto.SongPutDTO;
import com.kinnock.musicdiary.song.entity.Song;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {
  private final SongRepository songRepository;
  private final AlbumRepository albumRepository;
  private final DiaryUserRepository diaryUserRepository;
  private final ArtistRepository artistRepository;

  @Autowired
  public SongService(
      SongRepository songRepository,
      AlbumRepository albumRepository,
      DiaryUserRepository diaryUserRepository,
      ArtistRepository artistRepository
  ) {
    this.songRepository = songRepository;
    this.albumRepository = albumRepository;
    this.diaryUserRepository = diaryUserRepository;
    this.artistRepository = artistRepository;
  }

  public SongDTO createSong(SongPostDTO songPostDTO) {
    List<Artist> artists = this.artistRepository.findAllById(songPostDTO.getArtistIds());
    // TODO: DRY and consolidate all problems with the request body?
    if (artists.size() != songPostDTO.getArtistIds().size()) {
      Set<Long> foundArtistIds = artists.stream()
          .map(Artist::getId)
          .collect(Collectors.toUnmodifiableSet());
      List<Long> missingArtistIds = songPostDTO.getArtistIds()
          .stream()
          .filter(id -> !foundArtistIds.contains(id))
          .toList();
      throw new ResourceDoesNotExistException("artist", missingArtistIds);
    }
    DiaryUser diaryUser = this.diaryUserRepository.findById(songPostDTO.getSubmittedById())
        .orElseThrow(() -> new ResourceDoesNotExistException(
            "diaryUser", songPostDTO.getSubmittedById()));
    Optional<Album> optionalAlbum = Optional.ofNullable(songPostDTO.getAlbumId())
        .map(albumId -> this.albumRepository.findById(albumId)
            .orElseThrow(() -> new ResourceDoesNotExistException("album", albumId)));
    Song song = new Song(
        diaryUser,
        artists.stream().collect(Collectors.toUnmodifiableSet()),
        songPostDTO.getTitle(),
        optionalAlbum.orElse(null),
        songPostDTO.getLength(), // TODO: enforce positive
        songPostDTO.getLyricsUrl(),
        songPostDTO.getAlbumIndex() // TODO: enforce uniqueness and numeric order
    );
    return new SongDTO(this.songRepository.save(song));
  }

  // R
  public SongDTO getSongById(Long id) {
    Song song = this.songRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("song"));
    return new SongDTO(song);
  }

  // R - All
  public List<SongDTO> getAllSongs() {
    return this.songRepository.findAll().stream().map(SongDTO::new).toList();
  }

  // U
  public SongDTO updateSong(Long id, SongPutDTO songPutDTO) {
    Song song = this.songRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("song"));

    // artists
    EntityUtils.updateEntityValue(
        () -> {
          List<Artist> artistsToUpdate = this.artistRepository.findAllById(songPutDTO.getArtistIds());
          if (artistsToUpdate.size() != songPutDTO.getArtistIds().size()) {
            Set<Long> existingArtistIds = artistsToUpdate
                .stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());

            List<Long> missingArtistIds = songPutDTO.getArtistIds()
                .stream()
                .filter(artistId -> !existingArtistIds.contains(artistId))
                .toList();
            // throw some 400
            throw new ResourceDoesNotExistException("artist", missingArtistIds);
          }
          // using unmodifiable implementation results in an unsupported operation when saving
          return new HashSet<>(artistsToUpdate);
        },
        l -> !Objects.isNull(l) && !l.isEmpty(),
        song::setArtists
    );

    EntityUtils.updateNonBlankStringValue(songPutDTO::getTitle, song::setTitle);

    // album
    EntityUtils.updateNonNullEntityValue(
        () -> this.albumRepository.findById(songPutDTO.getAlbumId())
              .orElseThrow(() -> new ResourceDoesNotExistException(
                  "album", songPutDTO.getAlbumId())),
        song::setAlbum
    );

    // length
    // TODO: enforce positive
    EntityUtils.updateNonNullEntityValue(songPutDTO::getLength, song::setLength);

    // lyrics url
    // TODO: enforce existing url
    EntityUtils.updateNonNullEntityValue(songPutDTO::getLyricsUrl, song::setLyricsUrl);

    // album index
    // TODO: enforce ordering
    EntityUtils.updateNonNullEntityValue(songPutDTO::getAlbumIndex, song::setAlbumIndex);

    return new SongDTO(this.songRepository.save(song));
  }

  public void deleteSong(Long id) {
    Song song = this.songRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("song"));

    this.songRepository.delete(song);
  }
}
