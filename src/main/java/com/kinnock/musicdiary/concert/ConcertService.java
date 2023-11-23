package com.kinnock.musicdiary.concert;

import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.concert.dto.ConcertDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPostDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPutDTO;
import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConcertService {
  private final ConcertRepository concertRepository;
  private final DiaryUserRepository diaryUserRepository;
  private final ArtistRepository artistRepository;

  @Autowired
  public ConcertService(
      ConcertRepository concertRepository,
      DiaryUserRepository diaryUserRepository,
      ArtistRepository artistRepository
  ) {
    this.concertRepository = concertRepository;
    this.diaryUserRepository = diaryUserRepository;
    this.artistRepository = artistRepository;
  }

  public ConcertDTO createConcert(ConcertPostDTO concertPostDTO) {
    List<Artist> artists = this.artistRepository.findAllById(concertPostDTO.getArtistIds());
    DiaryUser diaryUser = this.diaryUserRepository.findById(concertPostDTO.getSubmittedById())
        .orElseThrow(); // TODO: bad request
    Concert concert = new Concert(
        diaryUser,
        artists,
        concertPostDTO.getTitle(),
        concertPostDTO.getDate(),
        concertPostDTO.getVenue(),
        List.of()
    );
    return new ConcertDTO(this.concertRepository.save(concert));
  }

  public ConcertDTO getConcertById(Long id) {
    Concert concert = this.concertRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("concert not found")); // TODO: 404
    return new ConcertDTO(concert);
  }

  public List<ConcertDTO> getAllConcerts() {
    return this.concertRepository.findAll().stream().map(ConcertDTO::new).toList();
  }

  public ConcertDTO updateConcert(Long id, ConcertPutDTO concertPutDTO) {
    Concert concert = this.concertRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("concert not found"));

    EntityUtils.updateEntityValue(
        () -> {
          List<Artist> artistsToUpdate = this.artistRepository.findAllById(concertPutDTO.getArtistIds());
          if (artistsToUpdate.size() != concertPutDTO.getArtistIds().size()) {
            Set<Long> existingArtistIds = artistsToUpdate
                .stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());

            List<Long> missingArtistIds = concertPutDTO.getArtistIds()
                .stream()
                .filter(artistId -> !existingArtistIds.contains(artistId))
                .toList();
            // throw some 400
            throw new IllegalStateException("invalid artist ids: " + missingArtistIds);
          }
          return artistsToUpdate;
        },
        l -> !Objects.isNull(l) && !l.isEmpty(),
        concert::setArtists
    );

    EntityUtils.updateNonBlankStringValue(concertPutDTO::getTitle, concert::setTitle);

    EntityUtils.updateNonNullEntityValue(concertPutDTO::getDate, concert::setDate);

    EntityUtils.updateNonNullEntityValue(concertPutDTO::getVenue, concert::setVenue);

    // TODO: when I get to service endpoints, move towards building concerts and set lists at same time

    return new ConcertDTO(concert);
  }

  public ConcertDTO deleteConcert(Long id) {
    Concert concert = this.concertRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("concert not found"));
    this.concertRepository.delete(concert);
    return new ConcertDTO(concert);
  }
}
