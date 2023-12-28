package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.entity.DiaryListEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryListEntryRepository extends JpaRepository<DiaryListEntry, Long> {
  List<DiaryListEntry> findByDiaryListId(Long diaryListId);
}
