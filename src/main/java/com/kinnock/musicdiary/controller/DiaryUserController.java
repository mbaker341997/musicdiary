package com.kinnock.musicdiary.controller;

import com.kinnock.musicdiary.entity.DiaryUser;
import com.kinnock.musicdiary.service.DiaryUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
public class DiaryUserController {

  private final DiaryUserService diaryUserService;

  @Autowired
  public DiaryUserController(DiaryUserService diaryUserService) {
    this.diaryUserService = diaryUserService;
  }

  // TODO: return a DTO
  // TODO: don't have a dump all users endpoint
  @GetMapping
  public List<DiaryUser> getUsers() {
    return this.diaryUserService.getAllDiaryUsers();
  }

  // TODO: C

  // TODO: R

  // TODO: U

  // TODO: D
}
