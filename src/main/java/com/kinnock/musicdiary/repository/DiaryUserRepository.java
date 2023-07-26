package com.kinnock.musicdiary.repository;

import com.kinnock.musicdiary.entity.DiaryUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryUserRepository extends JpaRepository<DiaryUser, Long> {
  Optional<DiaryUser> findByEmail(String email);

  Optional<DiaryUser> findByUsername(String username);

}
