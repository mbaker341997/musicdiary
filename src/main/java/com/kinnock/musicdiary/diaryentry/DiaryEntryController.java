package com.kinnock.musicdiary.diaryentry;

import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryDTO;
import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryPostDTO;
import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryPutDTO;
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
@RequestMapping(path = "api/v1/diary-entries")
public class DiaryEntryController {
  private final DiaryEntryService diaryEntryService;

  @Autowired
  public DiaryEntryController(DiaryEntryService diaryEntryService) {
    this.diaryEntryService = diaryEntryService;
  }

  @PostMapping
  public ResponseEntity<DiaryEntryDTO> createDiaryEntry(
      @RequestBody DiaryEntryPostDTO diaryEntryPostDTO
  ) {
    return new ResponseEntity<>(
        this.diaryEntryService.createDiaryEntry(diaryEntryPostDTO),
        HttpStatus.OK
    );
  }

  @GetMapping(path = "{diaryEntryId}")
  public ResponseEntity<DiaryEntryDTO> getAlbum(@PathVariable("diaryEntryId") Long diaryEntryId) {
    return new ResponseEntity<>(
        this.diaryEntryService.getDiaryEntryById(diaryEntryId),
        HttpStatus.OK
    );
  }

  @GetMapping
  public ResponseEntity<List<DiaryEntryDTO>> getAlbums() {
    return new ResponseEntity<>(this.diaryEntryService.getAllDiaryEntries(), HttpStatus.OK);
  }

  @PutMapping(path = "{diaryEntryId}")
  public ResponseEntity<DiaryEntryDTO> updateAlbum(
      @PathVariable("diaryEntryId") Long diaryEntryId,
      @RequestBody DiaryEntryPutDTO diaryEntryPutDTO
  ) {
    return new ResponseEntity<>(
        this.diaryEntryService.updateDiaryEntry(diaryEntryId, diaryEntryPutDTO),
        HttpStatus.OK
    );
  }

  @DeleteMapping(path = "{diaryEntryId}")
  public ResponseEntity<Void> deleteAlbum(@PathVariable("diaryEntryId") Long diaryEntryId) {
    this.diaryEntryService.deleteDiaryEntry(diaryEntryId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
