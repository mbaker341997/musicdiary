package com.kinnock.musicdiary.song;

import com.kinnock.musicdiary.song.entity.Song;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
  Optional<Song> findFirstByTitle(String title);
}
