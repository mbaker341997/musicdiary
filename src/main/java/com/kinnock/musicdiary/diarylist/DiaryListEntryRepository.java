package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.entity.DiaryListEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryListEntryRepository extends JpaRepository<DiaryListEntry, Long> {

}
