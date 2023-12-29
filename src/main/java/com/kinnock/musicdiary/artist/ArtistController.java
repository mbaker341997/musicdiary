package com.kinnock.musicdiary.artist;

import com.kinnock.musicdiary.artist.dto.ArtistDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPostDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPutDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/artists")
public class ArtistController {
  private final ArtistService artistService;

  @Autowired
  public ArtistController(ArtistService artistService) {
    this.artistService = artistService;
  }

  @PostMapping
  public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistPostDTO artistPostDTO) {
    return new ResponseEntity<>(
        this.artistService.createArtist(artistPostDTO),
        HttpStatus.OK
    );
  }

  @GetMapping(path = "{artistId}")
  public ResponseEntity<ArtistDTO> getArtist(@PathVariable("artistId") Long artistId) {
    return new ResponseEntity<>(
        this.artistService.getArtistById(artistId),
        HttpStatus.OK
    );
  }

  @GetMapping
  public List<ArtistDTO> getArtists() {
    return this.artistService.getAllArtists();
  }

  @PutMapping(path = "{artistId}")
  public ResponseEntity<ArtistDTO> updateArtist(
      @PathVariable("artistId") Long artistId,
      @RequestBody ArtistPutDTO artistPutDTO
  ) {
    return new ResponseEntity<>(
        this.artistService.updateArtist(
            artistId,
            artistPutDTO
        ),
        HttpStatus.OK
    );
  }

  @DeleteMapping(path = "{artistId}")
  public ResponseEntity<Void> deleteArtist(@PathVariable("artistId") Long artistId) {
    this.artistService.deleteArtist(artistId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
