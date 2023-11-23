package com.kinnock.musicdiary.concert;

import com.kinnock.musicdiary.concert.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

}
