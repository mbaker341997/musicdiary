package com.kinnock.musicdiary.artist;

import com.kinnock.musicdiary.artist.dto.ArtistDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPostDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPutDTO;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
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

    EntityUtils.updateNonBlankStringValue(putDTO::getName, artist::setName);

    EntityUtils.updateNonNullEntityValue(putDTO::getDateOfBirth, artist::setDateOfBirth);

    EntityUtils.updateNonNullEntityValue(putDTO::getBio, artist::setBio);

    EntityUtils.updateNonNullEntityValue(putDTO::getPictureUrl, artist::setPictureUrl);

    return new ArtistDTO(this.artistRepository.save(artist));
  }

  public void deleteArtist(Long id) {
    Artist artist = this.artistRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("artist not found")); // TODO: 404

    this.artistRepository.delete(artist);
  }
}
