package com.kinnock.musicdiary.concert;

import com.kinnock.musicdiary.concert.dto.ConcertDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPostDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPutDTO;
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
@RequestMapping(path = "api/v1/concerts")
public class ConcertController {
  private final ConcertService concertService;

  @Autowired
  public ConcertController(ConcertService concertService) {
    this.concertService = concertService;
  }

  @PostMapping
  public ResponseEntity<ConcertDTO> createConcert(@RequestBody ConcertPostDTO concertPostDTO) {
    return new ResponseEntity<>(this.concertService.createConcert(concertPostDTO), HttpStatus.OK);
  }

  @GetMapping(path = "{concertId}")
  public ResponseEntity<ConcertDTO> getConcert(@PathVariable("concertId") Long concertId) {
    try{
      return new ResponseEntity<>(this.concertService.getConcertById(concertId), HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping
  public ResponseEntity<List<ConcertDTO>> getConcerts() {
    return new ResponseEntity<>(this.concertService.getAllConcerts(), HttpStatus.OK);
  }

  @PutMapping(path = "{concertId}")
  public ResponseEntity<ConcertDTO> updateConcert(
      @PathVariable("concertId") Long concertId,
      @RequestBody ConcertPutDTO concertPutDTO
  ) {
    return new ResponseEntity<>(
        this.concertService.updateConcert(concertId, concertPutDTO),
        HttpStatus.OK
    );
  }

  @DeleteMapping(path = "{concertId}")
  public ResponseEntity<Void> deleteConcert(@PathVariable("concertId") Long concertId) {
    try {
      this.concertService.deleteConcert(concertId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
