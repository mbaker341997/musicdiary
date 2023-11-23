package com.kinnock.musicdiary.song;

import com.kinnock.musicdiary.album.AlbumRepository;
import com.kinnock.musicdiary.album.entity.Album;
import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.song.dto.SongDTO;
import com.kinnock.musicdiary.song.dto.SongPostDTO;
import com.kinnock.musicdiary.song.dto.SongPutDTO;
import com.kinnock.musicdiary.song.entity.Song;
import com.kinnock.musicdiary.utils.EntityUtils;
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
    DiaryUser diaryUser = this.diaryUserRepository.findById(songPostDTO.getSubmittedById())
        .orElseThrow(); // TODO: bad request
    Optional<Album> optionalAlbum = Optional.ofNullable(songPostDTO.getAlbumId())
        .map(albumId -> this.albumRepository.findById(albumId).orElseThrow()); // TODO: bad request
    Song song = new Song(
        diaryUser,
        artists,
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
        .orElseThrow(() -> new IllegalStateException("song not found")); // TODO: 404
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
        .orElseThrow(() -> new IllegalStateException("song not found")); // TODO: 404

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
            throw new IllegalStateException("invalid artist ids: " + missingArtistIds);
          }
          return artistsToUpdate;
        },
        l -> !Objects.isNull(l) && !l.isEmpty(),
        song::setArtists
    );

    EntityUtils.updateNonBlankStringValue(songPutDTO::getTitle, song::setTitle);

    // album
    EntityUtils.updateNonNullEntityValue(
        () -> this.albumRepository.findById(songPutDTO.getAlbumId())
              .orElseThrow(() -> new IllegalStateException("album not found")), // TODO: 404,
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

  // D
  public SongDTO deleteSong(Long id) {
    Song song = this.songRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("song not found")); // TODO: 404

    this.songRepository.delete(song);

    return new SongDTO(song);
  }
}
