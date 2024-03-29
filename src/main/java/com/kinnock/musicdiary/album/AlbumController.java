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
@RequestMapping(path = "api/v1/albums")
public class AlbumController {
  private final AlbumService albumService;

  @Autowired
  public AlbumController(AlbumService albumService) {
    this.albumService = albumService;
  }

  @PostMapping
  public ResponseEntity<AlbumDTO> createAlbum(@RequestBody AlbumPostDTO albumPostDTO) {
    return new ResponseEntity<>(this.albumService.createAlbum(albumPostDTO), HttpStatus.OK);
  }

  @GetMapping(path = "{albumId}")
  public ResponseEntity<AlbumDTO> getAlbum(@PathVariable("albumId") Long albumId) {
    return new ResponseEntity<>(this.albumService.getAlbumById(albumId), HttpStatus.OK);
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
    return new ResponseEntity<>(this.albumService.updateAlbum(albumId, albumPutDTO), HttpStatus.OK);
  }

  @DeleteMapping(path = "{albumId}")
  public ResponseEntity<Void> deleteAlbum(@PathVariable("albumId") Long albumId) {
      this.albumService.deleteAlbum(albumId);
      return new ResponseEntity<>(HttpStatus.OK);
  }
}
