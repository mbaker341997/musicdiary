package com.kinnock.musicdiary.diaryentry;

import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryDTO;
import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryPostDTO;
import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryPutDTO;
import com.kinnock.musicdiary.diaryentry.entity.DiaryEntry;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.exception.ResourceDoesNotExistException;
import com.kinnock.musicdiary.exception.ResourceNotFoundException;
import com.kinnock.musicdiary.loggable.LoggableRepository;
import com.kinnock.musicdiary.loggable.entity.Loggable;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryEntryService {
  private final DiaryEntryRepository diaryEntryRepository;
  private final DiaryUserRepository diaryUserRepository;
  private final LoggableRepository loggableRepository;

  @Autowired
  public DiaryEntryService(
      DiaryEntryRepository diaryEntryRepository,
      DiaryUserRepository diaryUserRepository,
      LoggableRepository loggableRepository
  ) {
    this.diaryEntryRepository = diaryEntryRepository;
    this.diaryUserRepository = diaryUserRepository;
    this.loggableRepository = loggableRepository;
  }

  public DiaryEntryDTO createDiaryEntry(DiaryEntryPostDTO postDTO) {
    DiaryUser diaryUser = this.diaryUserRepository.findById(postDTO.getUserId())
        .orElseThrow(() -> ResourceDoesNotExistException.from(
            "diaryUser", postDTO.getUserId()));
    Loggable loggable = this.loggableRepository.findById(postDTO.getLoggableId())
        .orElseThrow(() -> ResourceDoesNotExistException.from(
            "loggable", postDTO.getLoggableId()));

    DiaryEntry diaryEntry = new DiaryEntry(
        diaryUser,
        loggable,
        postDTO.getLogDate(),
        postDTO.getRating(),
        postDTO.getReview()
    );

    return new DiaryEntryDTO(this.diaryEntryRepository.save(diaryEntry));
  }

  public DiaryEntryDTO getDiaryEntryById(Long id) {
    DiaryEntry diaryEntry = this.diaryEntryRepository
        .findById(id)
        .orElseThrow(() -> ResourceNotFoundException.fromResourceName("diaryEntry"));
    return new DiaryEntryDTO(diaryEntry);
  }

  public List<DiaryEntryDTO> getAllDiaryEntries() {
    return this.diaryEntryRepository.findAll().stream().map(DiaryEntryDTO::new).toList();
  }

  public DiaryEntryDTO updateDiaryEntry(Long id, DiaryEntryPutDTO putDTO) {
    DiaryEntry diaryEntry = this.diaryEntryRepository
        .findById(id)
        .orElseThrow(() -> ResourceNotFoundException.fromResourceName("diaryEntry"));

    // TODO: no future dating? timezones will be tricky
    EntityUtils.updateNonNullEntityValue(putDTO::getLogDate, diaryEntry::setLogDate);

    EntityUtils.updateNonNullEntityValue(putDTO::getRating, diaryEntry::setRating);

    EntityUtils.updateNonBlankStringValue(putDTO::getReview, diaryEntry::setReview);

    return new DiaryEntryDTO(this.diaryEntryRepository.save(diaryEntry));
  }

  public void deleteDiaryEntry(Long id) {
    DiaryEntry diaryEntry = this.diaryEntryRepository
        .findById(id)
        .orElseThrow(() -> ResourceNotFoundException.fromResourceName("diaryEntry"));
    this.diaryEntryRepository.delete(diaryEntry);
  }
}
