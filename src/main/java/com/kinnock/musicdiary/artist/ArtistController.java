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
@RequestMapping(path = "api/v1/artist")
public class ArtistController {
  private final ArtistService artistService;

  @Autowired
  public ArtistController(ArtistService artistService) {
    this.artistService = artistService;
  }

  @PostMapping
  public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistPostDTO artistPostDTO) {
    try {
      return new ResponseEntity<>(
          this.artistService.createArtist(artistPostDTO),
          HttpStatus.OK
      );
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(path = "{artistId}")
  public ResponseEntity<ArtistDTO> getArtist(@PathVariable("artistId") Long artistId) {
    try {
      return new ResponseEntity<>(
          this.artistService.getArtistById(artistId),
          HttpStatus.OK
      );
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
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
    try {
      return new ResponseEntity<>(
          this.artistService.updateArtist(
              artistId,
              artistPutDTO
          ),
          HttpStatus.OK
      );
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping(path = "{artistId}")
  public ResponseEntity<Void> deleteArtist(@PathVariable("artistId") Long artistId) {
    try {
      this.artistService.deleteArtist(artistId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
