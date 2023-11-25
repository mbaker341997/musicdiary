package com.kinnock.musicdiary.song;

import com.kinnock.musicdiary.song.dto.SongDTO;
import com.kinnock.musicdiary.song.dto.SongPostDTO;
import com.kinnock.musicdiary.song.dto.SongPutDTO;
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
@RequestMapping(path = "api/v1/songs")
public class SongController {
  private final SongService songService;

  @Autowired
  public SongController(SongService songService) {
    this.songService = songService;
  }

  @PostMapping
  public ResponseEntity<SongDTO> createSong(@RequestBody SongPostDTO songPostDTO) {
    return new ResponseEntity<>(this.songService.createSong(songPostDTO), HttpStatus.OK);
  }

  @GetMapping(path = "{songId}")
  public ResponseEntity<SongDTO> getSong(@PathVariable("songId") Long songId) {
    try {
      return new ResponseEntity<>(this.songService.getSongById(songId), HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good exception system in place
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping
  public ResponseEntity<List<SongDTO>> getSongs() {
    return new ResponseEntity<>(this.songService.getAllSongs(), HttpStatus.OK);
  }

  @PutMapping(path = "{songId}")
  public ResponseEntity<SongDTO> updateSong(
      @PathVariable("songId") Long songId,
      @RequestBody SongPutDTO songPutDTO
  ) {
    return new ResponseEntity<>(this.songService.updateSong(songId, songPutDTO), HttpStatus.OK);
  }

  @DeleteMapping(path = "{songId}")
  public ResponseEntity<Void> deleteSong(@PathVariable("songId") Long songId) {
    this.songService.deleteSong(songId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
