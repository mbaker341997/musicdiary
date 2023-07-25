package com.kinnock.musicdiary.entity;

import java.time.LocalDate;

public class DiaryUser {
  private Long id;
  private String username;
  private String email;
  private String profileImageUrl;
  private Boolean isAdmin;
  private LocalDate dateOfBirth;

  public DiaryUser(Long id) {
    this.id = id;
  }

  public DiaryUser(
      Long id,
      String username,
      String email,
      String profileImageUrl,
      Boolean isAdmin,
      LocalDate dateOfBirth) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.profileImageUrl = profileImageUrl;
    this.isAdmin = isAdmin;
    this.dateOfBirth = dateOfBirth;
  }

  public DiaryUser(
      String username,
      String email,
      String profileImageUrl,
      Boolean isAdmin,
      LocalDate dateOfBirth) {
    this.username = username;
    this.email = email;
    this.profileImageUrl = profileImageUrl;
    this.isAdmin = isAdmin;
    this.dateOfBirth = dateOfBirth;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public Boolean getAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean admin) {
    isAdmin = admin;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
