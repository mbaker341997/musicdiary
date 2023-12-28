package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryPostDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryPutDTO;
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
@RequestMapping(path = "api/v1/diary-list-entries")
public class DiaryListEntryController {
  private final DiaryListService diaryListService;

  @Autowired
  public DiaryListEntryController(DiaryListService diaryListService) {
    this.diaryListService = diaryListService;
  }

  @PostMapping
  public ResponseEntity<DiaryListEntryDTO> createDiaryListEntry(
      @RequestBody DiaryListEntryPostDTO postDTO
  ) {
    try {
      return new ResponseEntity<>(
          this.diaryListService.createDiaryListEntry(postDTO),
          HttpStatus.OK
      );
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(path = "{diaryListEntryId}")
  public ResponseEntity<DiaryListEntryDTO> getDiaryListEntry(
      @PathVariable("diaryListEntryId") Long diaryListEntryId
  ) {
    try {
      return new ResponseEntity<>(
          this.diaryListService.getDiaryListEntryById(diaryListEntryId),
          HttpStatus.OK
      );
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping
  public ResponseEntity<List<DiaryListEntryDTO>> getDiaryListEntries() {
    return new ResponseEntity<>(this.diaryListService.getAllDiaryListEntries(), HttpStatus.OK);
  }

  @PutMapping(path = "{diaryListEntryId}")
  public ResponseEntity<DiaryListEntryDTO> updateDiaryListEntry(
      @PathVariable("diaryListEntryId") Long diaryListEntryId,
      @RequestBody DiaryListEntryPutDTO putDTO
  ) {
    return new ResponseEntity<>(
        this.diaryListService.updateDiaryListEntry(diaryListEntryId, putDTO),
        HttpStatus.OK
    );
  }

  @DeleteMapping(path = "{diaryListEntryId}")
  public ResponseEntity<Void> deleteDiaryListEntry(
      @PathVariable("diaryListEntryId") Long diaryListEntryId
  ) {
    try {
      this.diaryListService.deleteDiaryListEntry(diaryListEntryId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
