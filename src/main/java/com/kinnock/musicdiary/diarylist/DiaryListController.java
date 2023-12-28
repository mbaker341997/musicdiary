package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.dto.DiaryListDTO;
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

  private final DiaryListService diaryListService;

  @Autowired
  public DiaryListController(DiaryListService diaryListService) {
    this.diaryListService = diaryListService;
  }

  @PostMapping
  public ResponseEntity<DiaryListDTO> createDiaryList(@RequestBody DiaryListPostDTO postDTO) {
    try {
      return new ResponseEntity<>(this.diaryListService.createDiaryList(postDTO), HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(path = "{diaryListId}")
  public ResponseEntity<DiaryListDTO> getDiaryList(@PathVariable("diaryListId") Long diaryListId) {
    try {
      return new ResponseEntity<>(this.diaryListService.getDiaryListById(diaryListId), HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
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
    return new ResponseEntity<>(this.diaryListService.updateDiaryList(diaryListId, putDTO), HttpStatus.OK);
  }

  @DeleteMapping(path = "{diaryListId}")
  public ResponseEntity<Void> deleteDiaryList(@PathVariable("diaryListId") Long diaryListId) {
    try {
      this.diaryListService.deleteDiaryList(diaryListId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
