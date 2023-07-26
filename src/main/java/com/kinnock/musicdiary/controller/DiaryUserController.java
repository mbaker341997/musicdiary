package com.kinnock.musicdiary.controller;

import com.kinnock.musicdiary.entity.DiaryUser;
import com.kinnock.musicdiary.service.DiaryUserService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: use DTOs for everything instead of entity directly
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
  public DiaryUser createDiaryUser(@RequestBody DiaryUser diaryUser) {
    return this.diaryUserService.createDiaryUser(diaryUser);
  }

  // TODO: don't have a dump all users endpoint
  @GetMapping
  public List<DiaryUser> getUsers() {
    return this.diaryUserService.getAllDiaryUsers();
  }

  @GetMapping(path = "{userId}")
  public DiaryUser getUser(@PathVariable("userId") Long userId) {
    return this.diaryUserService.getDiaryUserById(userId);
  }

  // TODO: this one especially needs a DTO 
  @PutMapping(path = "{userId}")
  public DiaryUser updateDiaryUser(
      @PathVariable("userId") Long userId,
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String profileImageUrl,
      @RequestParam(required = false) Boolean isAdmin,
      @RequestParam(required = false) LocalDate dateOfBirth
  ) {
    return this.diaryUserService.updateDiaryUser(
        userId, 
        username, 
        email, 
        profileImageUrl, 
        isAdmin, 
        dateOfBirth
    );
  }

  @DeleteMapping(path = "{userId}")
  public void deleteDiaryUser(@PathVariable("userId") Long userId) {
    this.diaryUserService.deleteDiaryUser(userId);
  }
}
