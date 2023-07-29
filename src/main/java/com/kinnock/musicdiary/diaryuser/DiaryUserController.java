package com.kinnock.musicdiary.diaryuser;

import com.kinnock.musicdiary.diaryuser.dto.DiaryUserDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPostDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPutDTO;
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

// TODO: write some tests
@RestController
@RequestMapping(path = "api/v1/user")
public class DiaryUserController {

  private final DiaryUserService diaryUserService;

  @Autowired
  public DiaryUserController(DiaryUserService diaryUserService) {
    this.diaryUserService = diaryUserService;
  }

  @PostMapping
  public ResponseEntity<DiaryUserDTO> createDiaryUser(@RequestBody DiaryUserPostDTO diaryUserPostDTO) {
    return new ResponseEntity<>(
        this.diaryUserService.createDiaryUser(diaryUserPostDTO),
        HttpStatus.OK
    );
  }

  @GetMapping
  public List<DiaryUserDTO> getUsers() {
    return this.diaryUserService.getAllDiaryUsers();
  }

  @GetMapping(path = "{userId}")
  public ResponseEntity<DiaryUserDTO> getUser(@PathVariable("userId") Long userId) {
    try {
      return new ResponseEntity<>(
          this.diaryUserService.getDiaryUserById(userId),
          HttpStatus.OK
      );
    } catch(IllegalStateException e) {
      // TODO: refactor once there's a good Exception system in place
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping(path = "{userId}")
  public ResponseEntity<DiaryUserDTO> updateDiaryUser(
      @PathVariable("userId") Long userId,
      @RequestBody DiaryUserPutDTO diaryUserPutDTO
  ) {
    return new ResponseEntity<>(
        this.diaryUserService.updateDiaryUser(
            userId,
            diaryUserPutDTO
        ),
        HttpStatus.OK
    );
  }

  @DeleteMapping(path = "{userId}")
  public ResponseEntity<DiaryUserDTO> deleteDiaryUser(@PathVariable("userId") Long userId) {
    return new ResponseEntity<>(
        this.diaryUserService.deleteDiaryUser(userId),
        HttpStatus.OK
    );
  }
}
