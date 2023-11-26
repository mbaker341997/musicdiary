package com.kinnock.musicdiary.diaryentry;

import com.kinnock.musicdiary.diaryentry.entity.DiaryEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {
  List<DiaryEntry> findByDiaryUserId(Long id);
}
