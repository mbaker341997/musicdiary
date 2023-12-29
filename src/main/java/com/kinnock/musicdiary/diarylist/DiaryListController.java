package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.dto.DiaryListDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryPostDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryPutDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListPostDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListPutDTO;
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
@RequestMapping(path = "api/v1/diary-lists")
public class DiaryListController {
  private static final String ENTRIES_SUB_PATH = "entries";

  private final DiaryListService diaryListService;

  @Autowired
  public DiaryListController(DiaryListService diaryListService) {
    this.diaryListService = diaryListService;
  }

  @PostMapping
  public ResponseEntity<DiaryListDTO> createDiaryList(@RequestBody DiaryListPostDTO postDTO) {
      return new ResponseEntity<>(this.diaryListService.createDiaryList(postDTO), HttpStatus.OK);
  }

  @GetMapping(path = "{diaryListId}")
  public ResponseEntity<DiaryListDTO> getDiaryList(@PathVariable("diaryListId") Long diaryListId) {
      return new ResponseEntity<>(
          this.diaryListService.getDiaryListById(diaryListId),
          HttpStatus.OK
      );
  }

  @GetMapping
  public ResponseEntity<List<DiaryListDTO>> getDiaryLists() {
    return new ResponseEntity<>(this.diaryListService.getAllDiaryLists(), HttpStatus.OK);
  }

  @PutMapping(path = "{diaryListId}")
  public ResponseEntity<DiaryListDTO> updateDiaryList(
      @PathVariable("diaryListId") Long diaryListId,
      @RequestBody DiaryListPutDTO putDTO
  ) {
    return new ResponseEntity<>(
        this.diaryListService.updateDiaryList(diaryListId, putDTO),
        HttpStatus.OK
    );
  }

  @DeleteMapping(path = "{diaryListId}")
  public ResponseEntity<Void> deleteDiaryList(@PathVariable("diaryListId") Long diaryListId) {
    this.diaryListService.deleteDiaryList(diaryListId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(path = ENTRIES_SUB_PATH)
  public ResponseEntity<DiaryListEntryDTO> createDiaryListEntry(
      @RequestBody DiaryListEntryPostDTO postDTO
  ) {
    return new ResponseEntity<>(
        this.diaryListService.createDiaryListEntry(postDTO),
        HttpStatus.OK
    );
  }

  @GetMapping(path = ENTRIES_SUB_PATH + "/{diaryListEntryId}")
  public ResponseEntity<DiaryListEntryDTO> getDiaryListEntry(
      @PathVariable("diaryListEntryId") Long diaryListEntryId
  ) {
    return new ResponseEntity<>(
        this.diaryListService.getDiaryListEntryById(diaryListEntryId),
        HttpStatus.OK
    );
  }

  @GetMapping(path = ENTRIES_SUB_PATH)
  public ResponseEntity<List<DiaryListEntryDTO>> getDiaryListEntries() {
    return new ResponseEntity<>(this.diaryListService.getAllDiaryListEntries(), HttpStatus.OK);
  }

  @PutMapping(path = ENTRIES_SUB_PATH + "/{diaryListEntryId}")
  public ResponseEntity<DiaryListEntryDTO> updateDiaryListEntry(
      @PathVariable("diaryListEntryId") Long diaryListEntryId,
      @RequestBody DiaryListEntryPutDTO putDTO
  ) {
    return new ResponseEntity<>(
        this.diaryListService.updateDiaryListEntry(diaryListEntryId, putDTO),
        HttpStatus.OK
    );
  }

  @DeleteMapping(path = ENTRIES_SUB_PATH + "/{diaryListEntryId}")
  public ResponseEntity<Void> deleteDiaryListEntry(
      @PathVariable("diaryListEntryId") Long diaryListEntryId
  ) {
    this.diaryListService.deleteDiaryListEntry(diaryListEntryId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
