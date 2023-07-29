package com.kinnock.musicdiary.artist;

import com.kinnock.musicdiary.artist.entity.Artist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
  Optional<Artist> findByName(String name);
}
