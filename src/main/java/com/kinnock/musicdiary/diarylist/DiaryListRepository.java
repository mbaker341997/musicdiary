package com.kinnock.musicdiary.diarylist;

import com.kinnock.musicdiary.diarylist.entity.DiaryList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryListRepository extends JpaRepository<DiaryList, Long> {

}
