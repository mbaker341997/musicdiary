package com.kinnock.musicdiary.diaryuser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
public class DiaryUser {
  @Id
  @SequenceGenerator(
      name = "diary_user_sequence",
      sequenceName = "diary_user_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "diary_user_sequence"
  )
  private Long id;
  private String username;
  private String email;
  private String profileImageUrl;
  private Boolean isAdmin;
  private LocalDate dateOfBirth;

  public DiaryUser() {

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

  // TODO: do I need this setter?
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
