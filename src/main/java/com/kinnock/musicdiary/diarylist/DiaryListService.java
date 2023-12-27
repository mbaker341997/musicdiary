package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.dto.DiaryListDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListPostDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListPutDTO;
import com.kinnock.musicdiary.diarylist.entity.DiaryList;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryListService {
  private final DiaryListRepository diaryListRepository;
  private final DiaryUserRepository diaryUserRepository;

  @Autowired
  public DiaryListService(
      DiaryListRepository diaryListRepository,
      DiaryUserRepository diaryUserRepository
  ) {
    this.diaryListRepository = diaryListRepository;
    this.diaryUserRepository = diaryUserRepository;
  }

  public DiaryListDTO createDiaryList(DiaryListPostDTO diaryListPostDTO) {
    DiaryUser diaryUser = this.diaryUserRepository.findById(diaryListPostDTO.getDiaryUserId())
        .orElseThrow(); // TODO: bad request
    DiaryList diaryList = new DiaryList(
        diaryUser,
        Set.of(),
        diaryListPostDTO.getTitle(),
        diaryListPostDTO.getDescription()
    );
    return new DiaryListDTO(this.diaryListRepository.save(diaryList));
  }

  public DiaryListDTO getDiaryListById(Long id) {
    DiaryList diaryList = this.diaryListRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("diary list not found")); // TODO: 404
    return new DiaryListDTO(diaryList);
  }

  public List<DiaryListDTO> getAllDiaryLists() {
    return this.diaryListRepository
        .findAll()
        .stream()
        .map(DiaryListDTO::new)
        .toList();
  }

  public DiaryListDTO updateDiaryList(Long id, DiaryListPutDTO putDTO) {
    DiaryList diaryList = this.diaryListRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("diary list not found")); // TODO: 404

    EntityUtils.updateNonBlankStringValue(putDTO::getTitle, diaryList::setTitle);
    EntityUtils.updateNonBlankStringValue(putDTO::getDescription, diaryList::setDescription);

    return new DiaryListDTO(this.diaryListRepository.save(diaryList));
  }

  public void deleteDiaryList(Long id) {
    DiaryList diaryList = this.diaryListRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("diary list not found")); // TODO: 404

    this.diaryListRepository.delete(diaryList);
  }

  // List Entry
  // C
  // R
  // R All
  // U
  // D
}
