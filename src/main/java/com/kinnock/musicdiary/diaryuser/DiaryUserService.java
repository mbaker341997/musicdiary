package com.kinnock.musicdiary.diaryuser;

import com.kinnock.musicdiary.diaryuser.dto.DiaryUserDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPostDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPutDTO;
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

  public List<DiaryUserDTO> getAllDiaryUsers() {
    return diaryUserRepository.findAll().stream().map(DiaryUserDTO::new).toList();
  }

  public DiaryUserDTO getDiaryUserById(Long userId) {
    DiaryUser user = this.diaryUserRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalStateException("user not found")); // TODO: some 404
    return new DiaryUserDTO(user);
  }

  public DiaryUserDTO createDiaryUser(DiaryUserPostDTO diaryUserPostDTO) {
    Optional<DiaryUser> userByEmail = diaryUserRepository
        .findByEmail(diaryUserPostDTO.getEmail());
    Optional<DiaryUser> userByUsername = diaryUserRepository
        .findByUsername(diaryUserPostDTO.getUsername());

    if (userByEmail.isPresent()) {
      throw new IllegalStateException("email taken"); // TODO: my own error system?
    } else if (userByUsername.isPresent()) {
      throw new IllegalStateException("username taken");
    } else {
      DiaryUser newUser = new DiaryUser(
          diaryUserPostDTO.getUsername(),
          diaryUserPostDTO.getEmail(),
          diaryUserPostDTO.getIsAdmin(),
          diaryUserPostDTO.getDateOfBirth(),
          diaryUserPostDTO.getBio(),
          diaryUserPostDTO.getProfileImageUrl()
      );

      return new DiaryUserDTO(diaryUserRepository.save(newUser));
    }
  }

  public DiaryUserDTO deleteDiaryUser(Long userId) {
    DiaryUser user = diaryUserRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalStateException("user not found!")); // TODO: custom exception

    return new DiaryUserDTO(user);
  }

  public DiaryUserDTO updateDiaryUser(
      Long id,
      DiaryUserPutDTO diaryUserPutDTO
  ) {
    DiaryUser user = diaryUserRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("user not found")); // TODO: custom 404

    // TODO: enhanced username format validation
    if (diaryUserPutDTO.getUsername() != null
        && !diaryUserPutDTO.getUsername().isBlank()
        && !user.getUsername().equals(diaryUserPutDTO.getUsername())
    ) {
      Optional<DiaryUser> userByUsername =
          diaryUserRepository.findByUsername(diaryUserPutDTO.getUsername());
      if (userByUsername.isPresent()) {
        // TODO: throw a 400 or custom
        throw new IllegalStateException("username taken");
      }

      user.setUsername(diaryUserPutDTO.getUsername());
    }

    // email format validation
    if (diaryUserPutDTO.getEmail() != null 
        && !diaryUserPutDTO.getEmail().isBlank() 
        && !user.getEmail().equals(diaryUserPutDTO.getEmail())
    ) {
      Optional<DiaryUser> userByEmail = diaryUserRepository.findByEmail(diaryUserPutDTO.getEmail());
      if (userByEmail.isPresent()) {
        // TODO: throw a 400 or custom
        throw new IllegalStateException("email taken");
      }
      user.setEmail(diaryUserPutDTO.getEmail());
    }

    if (diaryUserPutDTO.getBio() != null
        && !Objects.equals(diaryUserPutDTO.getBio(), user.getBio())
    ) {
      user.setBio(diaryUserPutDTO.getBio());
    }

    if (diaryUserPutDTO.getProfileImageUrl() != null
        && !Objects.equals(diaryUserPutDTO.getProfileImageUrl(), user.getProfileImageUrl())
    ) {
      user.setProfileImageUrl(diaryUserPutDTO.getProfileImageUrl());
    }

    if (diaryUserPutDTO.getIsAdmin() != null
        && !Objects.equals(diaryUserPutDTO.getIsAdmin(), user.getIsAdmin())
    ) {
      user.setIsAdmin(diaryUserPutDTO.getIsAdmin());
    }

    if (diaryUserPutDTO.getDateOfBirth() != null
        && !Objects.equals(diaryUserPutDTO.getDateOfBirth(), user.getDateOfBirth())
    ) {
      user.setDateOfBirth(diaryUserPutDTO.getDateOfBirth());
    }
    
    return new DiaryUserDTO(diaryUserRepository.save(user));
  }
}
