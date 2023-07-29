package com.kinnock.musicdiary.artist;

import com.kinnock.musicdiary.artist.dto.ArtistDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPostDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPutDTO;
import com.kinnock.musicdiary.artist.entity.Artist;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

  private final ArtistRepository artistRepository;

  @Autowired
  public ArtistService(ArtistRepository artistRepository) {
    this.artistRepository = artistRepository;
  }

  public ArtistDTO createArtist(ArtistPostDTO postDTO) {
    Artist artist = new Artist(
            postDTO.getName(),
            postDTO.getDateOfBirth(),
            postDTO.getPictureUrl(),
            postDTO.getBio()
        );
    return new ArtistDTO(this.artistRepository.save(artist));
  }


  public ArtistDTO getArtistById(Long id) {
    Artist artist = this.artistRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("artist not found")); // TODO: some 404
    return new ArtistDTO(artist);
  }

  public List<ArtistDTO> getAllArtists() {
    return artistRepository.findAll().stream().map(ArtistDTO::new).toList();
  }

  // U
  public ArtistDTO updateArtist(Long id, ArtistPutDTO putDTO) {
    Artist artist = artistRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("artist not found")); // TODO: 404

    if (putDTO.getName() != null
        && !putDTO.getName().isBlank()
        && !Objects.equals(artist.getName(), putDTO.getName())
    ) {
      artist.setName(putDTO.getName());
    }

    if (putDTO.getDateOfBirth() != null
        && !Objects.equals(artist.getDateOfBirth(), putDTO.getDateOfBirth())
    ) {
      artist.setDateOfBirth(putDTO.getDateOfBirth());
    }

    if (putDTO.getBio() != null
        && !putDTO.getBio().isBlank()
        && !Objects.equals(artist.getBio(), putDTO.getBio())
    ) {
      artist.setBio(putDTO.getBio());
    }

    if (putDTO.getPictureUrl() != null
        && !putDTO.getPictureUrl().isBlank()
        && !Objects.equals(artist.getPictureUrl(), putDTO.getPictureUrl())
    ) {
      artist.setPictureUrl(putDTO.getPictureUrl());
    }

    return new ArtistDTO(artistRepository.save(artist));
  }

  public ArtistDTO deleteArtist(Long id) {
    Artist artist = artistRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("artist not found")); // TODO: 404

    artistRepository.delete(artist);

    return new ArtistDTO(artist);
  }
}
