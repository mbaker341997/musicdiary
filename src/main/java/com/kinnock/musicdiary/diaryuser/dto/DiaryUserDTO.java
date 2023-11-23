package com.kinnock.musicdiary.diaryuser.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryUserDTO {
  private final Long id;
  private final String username;
  private final String email;
  private final String bio;
  private final String profileImageUrl;
  private final Boolean isAdmin;

  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate dateOfBirth;

  @JsonCreator
  public DiaryUserDTO(
      Long id,
      String username,
      String email,
      String bio,
      String profileImageUrl,
      Boolean isAdmin,
      LocalDate dateOfBirth
  ) {
    // TODO: nonnull validations?
    this.id = id;
    this.username = username;
    this.email = email;
    this.bio = bio;
    this.profileImageUrl = profileImageUrl;
    this.isAdmin = isAdmin;
    this.dateOfBirth = dateOfBirth;
  }

  public DiaryUserDTO(DiaryUser diaryUser) {
    this(
        diaryUser.getId(),
        diaryUser.getUsername(),
        diaryUser.getEmail(),
        diaryUser.getBio(),
        diaryUser.getProfileImageUrl(),
        diaryUser.getIsAdmin(),
        diaryUser.getDateOfBirth()
    );
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getBio() {
    return bio;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }
}
