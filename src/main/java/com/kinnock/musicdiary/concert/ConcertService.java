package com.kinnock.musicdiary.concert;

import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.concert.dto.ConcertDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPostDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPutDTO;
import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.utils.exception.ResourceDoesNotExistException;
import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import com.kinnock.musicdiary.setlistitem.SetListItemRepository;
import com.kinnock.musicdiary.utils.EntityUtils;
import jakarta.transaction.Transactional;
import java.util.HashSet;
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
  private final SetListItemRepository setListItemRepository;

  @Autowired
  public ConcertService(
      ConcertRepository concertRepository,
      DiaryUserRepository diaryUserRepository,
      ArtistRepository artistRepository,
      SetListItemRepository setListItemRepository
  ) {
    this.concertRepository = concertRepository;
    this.diaryUserRepository = diaryUserRepository;
    this.artistRepository = artistRepository;
    this.setListItemRepository = setListItemRepository;
  }

  public ConcertDTO createConcert(ConcertPostDTO concertPostDTO) {
    List<Artist> artists = this.artistRepository.findAllById(concertPostDTO.getArtistIds());
    if (artists.size() != concertPostDTO.getArtistIds().size()) {
      Set<Long> foundArtistIds = artists.stream()
          .map(Artist::getId)
          .collect(Collectors.toUnmodifiableSet());
      List<Long> missingArtistIds = concertPostDTO.getArtistIds()
          .stream()
          .filter(id -> !foundArtistIds.contains(id))
          .toList();
      throw new ResourceDoesNotExistException("artist", missingArtistIds);
    }

    DiaryUser diaryUser = this.diaryUserRepository.findById(concertPostDTO.getSubmittedById())
        .orElseThrow(() -> new ResourceDoesNotExistException(
            "diaryUser", concertPostDTO.getSubmittedById()));
    Concert concert = new Concert(
        diaryUser,
        artists.stream().collect(Collectors.toUnmodifiableSet()),
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
        .orElseThrow(() -> new ResourceNotFoundException("concert"));
    return new ConcertDTO(concert);
  }

  public List<ConcertDTO> getAllConcerts() {
    return this.concertRepository.findAll().stream().map(ConcertDTO::new).toList();
  }

  public ConcertDTO updateConcert(Long id, ConcertPutDTO concertPutDTO) {
    Concert concert = this.concertRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("concert"));

    EntityUtils.updateEntityValue(
        () -> {
          List<Artist> artistsToUpdate = this.artistRepository.findAllById(concertPutDTO.getArtistIds());
          if (artistsToUpdate.size() != concertPutDTO.getArtistIds().size()) {
            Set<Long> existingArtistIds = artistsToUpdate
                .stream()
                .map(Artist::getId)
                .collect(Collectors.toUnmodifiableSet());

            List<Long> missingArtistIds = concertPutDTO.getArtistIds()
                .stream()
                .filter(artistId -> !existingArtistIds.contains(artistId))
                .toList();

            throw new ResourceDoesNotExistException("artist", missingArtistIds);
          }
          // using unmodifiable implementation results in an unsupported operation when saving
          return new HashSet<>(artistsToUpdate);
        },
        l -> !Objects.isNull(l) && !l.isEmpty(),
        concert::setArtists
    );

    EntityUtils.updateNonBlankStringValue(concertPutDTO::getTitle, concert::setTitle);

    EntityUtils.updateNonNullEntityValue(concertPutDTO::getDate, concert::setDate);

    EntityUtils.updateNonNullEntityValue(concertPutDTO::getVenue, concert::setVenue);

    // TODO: when I get to service endpoints, move towards building concerts and set lists at same time

    return new ConcertDTO(this.concertRepository.save(concert));
  }

  @Transactional
  public void deleteConcert(Long id) {
    Concert concert = this.concertRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("concert"));
    concert.getSetListItems().forEach(this.setListItemRepository::delete);
    this.concertRepository.delete(concert);
  }
}
