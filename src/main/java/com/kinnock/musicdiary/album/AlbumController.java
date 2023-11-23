package com.kinnock.musicdiary.album;

import com.kinnock.musicdiary.album.dto.AlbumDTO;
import com.kinnock.musicdiary.album.dto.AlbumPostDTO;
import com.kinnock.musicdiary.album.dto.AlbumPutDTO;
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
@RequestMapping(path = "api/v1/album")
public class AlbumController {
  private final AlbumService albumService;

  @Autowired
  public AlbumController(AlbumService albumService) {
    this.albumService = albumService;
  }

  @PostMapping
  public ResponseEntity<AlbumDTO> createAlbum(@RequestBody AlbumPostDTO albumPostDTO) {
    try {
      return new ResponseEntity<>(this.albumService.createAlbum(albumPostDTO), HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(path = "{albumId}")
  public ResponseEntity<AlbumDTO> getAlbum(@PathVariable("albumId") Long albumId) {
    try {
      return new ResponseEntity<>(this.albumService.getAlbumById(albumId), HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping
  public ResponseEntity<List<AlbumDTO>> getAlbums() {
    return new ResponseEntity<>(this.albumService.getAllAlbums(), HttpStatus.OK);
  }

  @PutMapping(path = "{albumId}")
  public ResponseEntity<AlbumDTO> updateAlbum(
      @PathVariable("albumId") Long albumId,
      @RequestBody AlbumPutDTO albumPutDTO
  ) {
    // TODO: call the update method
    return new ResponseEntity<>(this.albumService.updateAlbum(albumId, albumPutDTO), HttpStatus.OK);
  }

  @DeleteMapping(path = "{albumId}")
  public ResponseEntity<Void> deleteAlbum(@PathVariable("albumId") Long albumId) {
    try {
      this.albumService.deleteAlbum(albumId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
