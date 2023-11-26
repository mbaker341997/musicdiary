package com.kinnock.musicdiary.loggable;

import com.kinnock.musicdiary.loggable.entity.Loggable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggableRepository extends JpaRepository<Loggable, Long> {

}
