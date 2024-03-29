package com.kinnock.musicdiary.diaryuser;

import com.kinnock.musicdiary.diaryuser.dto.DiaryUserDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPostDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPutDTO;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
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
    return this.diaryUserRepository.findAll().stream().map(DiaryUserDTO::new).toList();
  }

  public DiaryUserDTO getDiaryUserById(Long userId) {
    DiaryUser user = this.diaryUserRepository
        .findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("diaryUser"));
    return new DiaryUserDTO(user);
  }

  public DiaryUserDTO createDiaryUser(DiaryUserPostDTO diaryUserPostDTO) {
    Optional<DiaryUser> userByEmail = diaryUserRepository
        .findByEmail(diaryUserPostDTO.getEmail());
    Optional<DiaryUser> userByUsername = diaryUserRepository
        .findByUsername(diaryUserPostDTO.getUsername());

    if (userByEmail.isPresent()) {
      throw new BadUserDataException("email taken");
    } else if (userByUsername.isPresent()) {
      throw new BadUserDataException("username taken");
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

  public void deleteDiaryUser(Long userId) {
    DiaryUser user = diaryUserRepository
        .findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("diaryUser"));

    this.diaryUserRepository.delete(user);
  }

  public DiaryUserDTO updateDiaryUser(
      Long id,
      DiaryUserPutDTO diaryUserPutDTO
  ) {
    DiaryUser user = this.diaryUserRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("diaryUser"));

    // TODO: enhanced username format validation
    EntityUtils.updateNonBlankStringValue(
        () -> {
          var dtoValue = diaryUserPutDTO.getUsername();
          if (this.diaryUserRepository.findByUsername(dtoValue).isPresent()) {
            throw new BadUserDataException("username taken");
          }
          return dtoValue;
        },
        user::setUsername
    );

    // TODO: email format validation
    EntityUtils.updateNonBlankStringValue(
        () -> {
          var dtoValue = diaryUserPutDTO.getEmail();
          if (this.diaryUserRepository.findByEmail(dtoValue).isPresent()) {
            throw new BadUserDataException("email taken");
          }
          return dtoValue;
        }, user::setEmail
    );

    EntityUtils.updateNonNullEntityValue(diaryUserPutDTO::getBio, user::setBio);

    EntityUtils.updateNonNullEntityValue(
        diaryUserPutDTO::getProfileImageUrl,
        user::setProfileImageUrl
    );

    EntityUtils.updateNonNullEntityValue(diaryUserPutDTO::getIsAdmin, user::setIsAdmin);

    EntityUtils.updateNonNullEntityValue(
        diaryUserPutDTO::getDateOfBirth,
        user::setDateOfBirth
    );
    
    return new DiaryUserDTO(this.diaryUserRepository.save(user));
  }
}
