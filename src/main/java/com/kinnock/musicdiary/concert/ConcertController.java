package com.kinnock.musicdiary.concert;

import com.kinnock.musicdiary.concert.dto.ConcertDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPostDTO;
import com.kinnock.musicdiary.concert.dto.SetListItemPostDTO;
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
@RequestMapping(path = "api/v1/concert")
public class ConcertController {
  @Autowired
  public ConcertController() {

  }

  @PostMapping
  public ResponseEntity<ConcertDTO> createConcert(@RequestBody ConcertPostDTO concertPostDTO) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(path = "{concertId}")
  public ResponseEntity<ConcertDTO> getConcert(@PathVariable("concertId") Long concertId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<ConcertDTO> getConcerts() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(path = "{concertId}")
  public ResponseEntity<ConcertDTO> updateConcert(@PathVariable("concertId") Long concertId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(path = "{concertId}")
  public ResponseEntity<ConcertDTO> deleteConcert(@PathVariable("concertId") Long concertId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(path = "{concertId}")
  public ResponseEntity<ConcertDTO> createSetListItem(
      @PathVariable("concertId") Long concertId,
      @RequestBody SetListItemPostDTO setListItemPostDTO) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(path = "{setListItemId}")
  public ResponseEntity<ConcertDTO> updateSetListItem(@PathVariable("setListItemId") Long setListItemId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(path = "{setListItemId}")
  public ResponseEntity<ConcertDTO> deleteSetListItem(@PathVariable("setListItemId") Long setListItemId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
