package com.kinnock.musicdiary.diaryuser;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryUserService {

  private final DiaryUserRepository diaryUserRepository;

  @Autowired
  public DiaryUserService(DiaryUserRepository diaryUserRepository) {
    this.diaryUserRepository = diaryUserRepository;
  }

  public List<DiaryUser> getAllDiaryUsers() {
    return diaryUserRepository.findAll();
  }

  public DiaryUser getDiaryUserById(Long userId) {
    return this.diaryUserRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalStateException("user not found")); // TODO: some 404
  }

  public DiaryUser createDiaryUser(DiaryUser diaryUser) {
    Optional<DiaryUser> userByEmail = diaryUserRepository.findByEmail(diaryUser.getEmail());
    Optional<DiaryUser> userByUsername = diaryUserRepository.findByUsername(diaryUser.getUsername());

    if (userByEmail.isPresent()) {
      throw new IllegalStateException("email taken"); // TODO: my own error system?
    } else if (userByUsername.isPresent()) {
      throw new IllegalStateException("username taken");
    } else {
      return diaryUserRepository.save(diaryUser);
    }
  }

  public void deleteDiaryUser(Long userId) {
    diaryUserRepository.findById(userId).ifPresentOrElse(
        (diaryUser) -> diaryUserRepository.deleteById(userId),
        () -> {
          throw new IllegalStateException("user not found!");
        }
    );
  }

  @Transactional
  public DiaryUser updateDiaryUser(
      Long id,
      String username,
      String email,
      String bio,
      String profileImageUrl,
      Boolean isAdmin,
      LocalDate dateOfBirth
  ) {
    DiaryUser user = diaryUserRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("user not found")); // TODO: custom 404

    // TODO: enhanced username format validation
    if (username != null && !username.isBlank() && !user.getUsername().equals(username)) {
      Optional<DiaryUser> userByUsername = diaryUserRepository.findByUsername(username);
      if (userByUsername.isPresent()) {
        // TODO: throw a 400 or custom
        throw new IllegalStateException("username taken");
      }
      user.setUsername(username);
    }

    // email format validation
    if (email != null && email.length() > 0 && !Objects.equals(email, user.getEmail())) {
      Optional<DiaryUser> userByEmail = diaryUserRepository.findByEmail(email);
      if (userByEmail.isPresent()) {
        // TODO: throw a 400 or custom
        throw new IllegalStateException("email taken");
      }
      user.setEmail(email);
    }

    if (bio != null && !Objects.equals(bio, user.getBio())) {
      user.setBio(bio);
    }

    if (profileImageUrl != null && !Objects.equals(profileImageUrl, user.getProfileImageUrl())) {
      user.setProfileImageUrl(profileImageUrl);
    }

    if (isAdmin != null && !Objects.equals(isAdmin, user.getIsAdmin())) {
      user.setIsAdmin(isAdmin);
    }

    if (dateOfBirth != null && !Objects.equals(dateOfBirth, user.getDateOfBirth())) {
      user.setDateOfBirth(dateOfBirth);
    }

    return user;
  }
}
