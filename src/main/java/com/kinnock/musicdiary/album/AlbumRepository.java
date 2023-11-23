package com.kinnock.musicdiary.album;

import com.kinnock.musicdiary.album.entity.Album;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
  Optional<Album> findFirstByTitle(String title);
}
