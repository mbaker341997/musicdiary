package com.kinnock.musicdiary.album;

import com.kinnock.musicdiary.album.dto.AlbumPostDTO;
import com.kinnock.musicdiary.album.dto.AlbumDTO;
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
  @Autowired
  public AlbumController() {
    
  }

  @PostMapping
  public ResponseEntity<AlbumDTO> createAlbum(@RequestBody AlbumPostDTO albumPostDTO) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(path = "{albumId}")
  public ResponseEntity<AlbumDTO> getAlbum(@PathVariable("albumId") Long albumId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<AlbumDTO> getAlbums() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(path = "{albumId}")
  public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable("albumId") Long albumId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(path = "{albumId}")
  public ResponseEntity<AlbumDTO> deleteAlbum(@PathVariable("albumId") Long albumId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
