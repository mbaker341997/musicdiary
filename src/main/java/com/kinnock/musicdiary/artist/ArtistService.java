package com.kinnock.musicdiary.artist;

import com.kinnock.musicdiary.artist.dto.ArtistDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPostDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPutDTO;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
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
    return this.artistRepository.findAll().stream().map(ArtistDTO::new).toList();
  }

  public ArtistDTO updateArtist(Long id, ArtistPutDTO putDTO) {
    Artist artist = this.artistRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("artist not found")); // TODO: 404

    artist.setName(EntityUtils.resolveUpdatedFieldValue(
        putDTO::getName,
        StringUtils::isNotBlank,
        artist::getName
    ));

    artist.setDateOfBirth(EntityUtils.resolveUpdatedFieldValue(
        putDTO::getDateOfBirth,
        Objects::nonNull,
        artist::getDateOfBirth
    ));

    artist.setBio(EntityUtils.resolveUpdatedFieldValue(
        putDTO::getBio,
        Objects::nonNull,
        artist::getBio
    ));

    artist.setPictureUrl(EntityUtils.resolveUpdatedFieldValue(
        putDTO::getPictureUrl,
        Objects::nonNull,
        artist::getPictureUrl
    ));

    return new ArtistDTO(this.artistRepository.save(artist));
  }

  public ArtistDTO deleteArtist(Long id) {
    Artist artist = this.artistRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("artist not found")); // TODO: 404

    this.artistRepository.delete(artist);

    return new ArtistDTO(artist);
  }
}
