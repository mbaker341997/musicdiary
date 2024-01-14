package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.dto.DiaryListDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryPostDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryPutDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListPostDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListPutDTO;
import com.kinnock.musicdiary.diarylist.entity.DiaryList;
import com.kinnock.musicdiary.diarylist.entity.DiaryListEntry;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.exception.ResourceDoesNotExistException;
import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import com.kinnock.musicdiary.loggable.LoggableRepository;
import com.kinnock.musicdiary.loggable.entity.Loggable;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryListService {
  private final DiaryListRepository diaryListRepository;
  private final DiaryListEntryRepository diaryListEntryRepository;
  private final DiaryUserRepository diaryUserRepository;
  private final LoggableRepository loggableRepository;

  @Autowired
  public DiaryListService(
      DiaryListRepository diaryListRepository,
      DiaryListEntryRepository diaryListEntryRepository,
      DiaryUserRepository diaryUserRepository,
      LoggableRepository loggableRepository
  ) {
    this.diaryListRepository = diaryListRepository;
    this.diaryListEntryRepository = diaryListEntryRepository;
    this.diaryUserRepository = diaryUserRepository;
    this.loggableRepository = loggableRepository;
  }

  public DiaryListDTO createDiaryList(DiaryListPostDTO diaryListPostDTO) {
    DiaryUser diaryUser = this.diaryUserRepository.findById(diaryListPostDTO.getDiaryUserId())
        .orElseThrow(() -> new ResourceDoesNotExistException(
            "diaryUser", diaryListPostDTO.getDiaryUserId()));
    DiaryList diaryList = new DiaryList(
        diaryUser,
        List.of(),
        diaryListPostDTO.getTitle(),
        diaryListPostDTO.getDescription()
    );
    return new DiaryListDTO(this.diaryListRepository.save(diaryList));
  }

  public DiaryListDTO getDiaryListById(Long id) {
    DiaryList diaryList = this.diaryListRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("diaryList"));
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
        .orElseThrow(() -> new ResourceNotFoundException("diaryList"));

    EntityUtils.updateNonBlankStringValue(putDTO::getTitle, diaryList::setTitle);
    EntityUtils.updateNonBlankStringValue(putDTO::getDescription, diaryList::setDescription);

    return new DiaryListDTO(this.diaryListRepository.save(diaryList));
  }

  public void deleteDiaryList(Long id) {
    DiaryList diaryList = this.diaryListRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("diaryList"));

    this.diaryListRepository.delete(diaryList);
  }

  // List Entry
  public DiaryListEntryDTO createDiaryListEntry(DiaryListEntryPostDTO diaryListEntryPostDTO) {
    DiaryList diaryList = this.diaryListRepository.findById(diaryListEntryPostDTO.getDiaryListId())
        .orElseThrow(() -> new ResourceDoesNotExistException(
            "diaryList", diaryListEntryPostDTO.getDiaryListId()));
    Loggable loggable = this.loggableRepository.findById(diaryListEntryPostDTO.getLoggableId())
        .orElseThrow(() -> new ResourceDoesNotExistException(
            "loggable", diaryListEntryPostDTO.getLoggableId()));
    DiaryListEntry diaryListEntry = new DiaryListEntry(
        diaryList,
        loggable,
        diaryListEntryPostDTO.getListIndex(), // TODO: ordering entry
        diaryListEntryPostDTO.getNote()
    );
    return new DiaryListEntryDTO(this.diaryListEntryRepository.save(diaryListEntry));
  }

  public DiaryListEntryDTO getDiaryListEntryById(Long id) {
    DiaryListEntry diaryListEntry = this.diaryListEntryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("diaryListEntry"));
    return new DiaryListEntryDTO(diaryListEntry);
  }

  public List<DiaryListEntryDTO> getAllDiaryListEntries() {
    return this.diaryListEntryRepository
        .findAll()
        .stream()
        .map(DiaryListEntryDTO::new)
        .toList();
  }

  public DiaryListEntryDTO updateDiaryListEntry(Long id, DiaryListEntryPutDTO putDTO) {
    DiaryListEntry diaryListEntry = this.diaryListEntryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("diaryListEntry"));

    EntityUtils.updateNonNullEntityValue(
        () -> this.loggableRepository
            .findById(putDTO.getLoggableId())
            .orElseThrow(() -> new ResourceDoesNotExistException(
                "loggable", putDTO.getLoggableId())),
        diaryListEntry::setLoggable
    );

    EntityUtils.updateNonNullEntityValue(putDTO::getNote, diaryListEntry::setNote);

    // set index
    // TODO: enforce ordering
    EntityUtils.updateNonNullEntityValue(putDTO::getListIndex, diaryListEntry::setListIndex);

    return new DiaryListEntryDTO(this.diaryListEntryRepository.save(diaryListEntry));
  }

  public void deleteDiaryListEntry(Long id) {
    DiaryListEntry diaryListEntry = this.diaryListEntryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("diaryListEntry"));

    this.diaryListEntryRepository.delete(diaryListEntry);
  }
}
