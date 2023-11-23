package com.kinnock.musicdiary.diaryuser.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kinnock.musicdiary.utils.DateTimeUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryUserPostDTO {
  @NotEmpty
  @Size(min = 3, message = "username should be at least 3 characters long")
  private final String username;
  @NotEmpty
  @Email
  private final String email;
  @NotNull
  private final Boolean isAdmin;
  @NotNull
  @JsonFormat(shape = Shape.STRING, pattern = DateTimeUtils.LOCAL_DATE_FORMAT)
  private final LocalDate dateOfBirth;
  private final String bio;
  private final String profileImageUrl;

  @JsonCreator
  public DiaryUserPostDTO(
      String username,
      String email,
      String bio,
      String profileImageUrl,
      Boolean isAdmin,
      LocalDate dateOfBirth
  ) {
    this.username = username;
    this.email = email;
    this.bio = bio;
    this.profileImageUrl = profileImageUrl;
    this.isAdmin = isAdmin;
    this.dateOfBirth = dateOfBirth;
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
