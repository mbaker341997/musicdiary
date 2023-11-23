package com.kinnock.musicdiary.diaryuser.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table
public class DiaryUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String username;
  @NotNull
  private String email;
  @NotNull
  private Boolean isAdmin;
  @NotNull
  private LocalDate dateOfBirth;
  private String bio;
  private String profileImageUrl;

  public DiaryUser() {

  }

  public DiaryUser(
      String username,
      String email,
      Boolean isAdmin,
      LocalDate dateOfBirth,
      String bio,
      String profileImageUrl
  ) {
    this.username = username;
    this.email = email;
    this.isAdmin = isAdmin;
    this.dateOfBirth = dateOfBirth;
    this.bio = bio;
    this.profileImageUrl = profileImageUrl;
  }

  public Long getId() {
    return id;
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

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
