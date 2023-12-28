package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.entity.DiaryList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryListRepository extends JpaRepository<DiaryList, Long> {
  List<DiaryList> findByDiaryUserId(Long diaryUserId);
}
