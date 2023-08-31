package com.kinnock.musicdiary.diaryuser;

import com.kinnock.musicdiary.diaryuser.dto.DiaryUserDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPostDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPutDTO;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
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
    return this.diaryUserRepository.findAll().stream().map(DiaryUserDTO::new).toList();
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

      return new DiaryUserDTO(this.diaryUserRepository.save(newUser));
    }
  }

  public DiaryUserDTO deleteDiaryUser(Long userId) {
    DiaryUser user = diaryUserRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalStateException("user not found!")); // TODO: custom exception

    this.diaryUserRepository.delete(user);

    return new DiaryUserDTO(user);
  }

  public DiaryUserDTO updateDiaryUser(
      Long id,
      DiaryUserPutDTO diaryUserPutDTO
  ) {
    DiaryUser user = this.diaryUserRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("user not found")); // TODO: custom 404

    // TODO: enhanced username format validation
    user.setUsername(EntityUtils.resolveUpdatedFieldValue(
        () -> {
          var dtoValue = diaryUserPutDTO.getUsername();
          if (this.diaryUserRepository.findByUsername(dtoValue).isPresent()) {
            // TODO: throw a 400 or custom
            throw new IllegalStateException("username taken");
          }
          return dtoValue;
        },
        StringUtils::isNotBlank,
        user::getUsername
      )
    );

    user.setEmail(EntityUtils.resolveUpdatedFieldValue(
        () -> {
          var dtoValue = diaryUserPutDTO.getEmail();
          if (this.diaryUserRepository.findByEmail(dtoValue).isPresent()) {
            // TODO: throw a 400 or custom
            throw new IllegalStateException("email taken");
          }
          return dtoValue;
        },
        StringUtils::isNotBlank,
        user::getEmail
      )
    );

    user.setBio(EntityUtils.resolveUpdatedFieldValue(
        diaryUserPutDTO::getBio,
        Objects::nonNull,
        user::getBio
    ));

    user.setProfileImageUrl(EntityUtils.resolveUpdatedFieldValue(
        diaryUserPutDTO::getProfileImageUrl,
        Objects::nonNull,
        user::getProfileImageUrl
    ));

    user.setIsAdmin(EntityUtils.resolveUpdatedFieldValue(
        diaryUserPutDTO::getIsAdmin,
        Objects::nonNull,
        user::getIsAdmin
    ));

    user.setDateOfBirth(EntityUtils.resolveUpdatedFieldValue(
        diaryUserPutDTO::getDateOfBirth,
        Objects::nonNull,
        user::getDateOfBirth
    ));
    
    return new DiaryUserDTO(this.diaryUserRepository.save(user));
  }
}
