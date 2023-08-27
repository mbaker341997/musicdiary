package com.kinnock.musicdiary.song;

import com.kinnock.musicdiary.song.dto.SongDTO;
import com.kinnock.musicdiary.song.dto.SongPostDTO;
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
@RequestMapping(path = "api/v1/song")
public class SongController {
  private final SongService songService;

  @Autowired
  public SongController(SongService songService) {
    this.songService = songService;
  }

  @PostMapping
  public ResponseEntity<SongDTO> createSong(@RequestBody SongPostDTO songPostDTO) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(path = "{songId}")
  public ResponseEntity<SongDTO> getSong(@PathVariable("songId") Long songId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<SongDTO> getSongs() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(path = "{songId}")
  public ResponseEntity<SongDTO> updateSong(@PathVariable("songId") Long songId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(path = "{songId}")
  public ResponseEntity<SongDTO> deleteSong(@PathVariable("songId") Long songId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
