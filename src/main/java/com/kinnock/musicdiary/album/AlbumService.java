package com.kinnock.musicdiary.album;

import com.kinnock.musicdiary.album.dto.AlbumDTO;
import com.kinnock.musicdiary.album.dto.AlbumPostDTO;
import com.kinnock.musicdiary.album.entity.Album;
import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import java.util.List;
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

  // TODO - U

  // D
  public AlbumDTO deleteAlbum(Long id) {
    Album album = this.albumRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("album not found")); // TODO: some 404

    albumRepository.delete(album);

    return new AlbumDTO(album);
  }
}
