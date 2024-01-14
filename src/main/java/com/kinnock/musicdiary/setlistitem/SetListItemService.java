package com.kinnock.musicdiary.setlistitem;

import com.kinnock.musicdiary.concert.ConcertRepository;
import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.utils.exception.ResourceDoesNotExistException;
import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemDTO;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemPostDTO;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemPutDTO;
import com.kinnock.musicdiary.setlistitem.entity.SetListItem;
import com.kinnock.musicdiary.song.SongRepository;
import com.kinnock.musicdiary.song.entity.Song;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetListItemService {
  private final ConcertRepository concertRepository;
  private final SetListItemRepository setListItemRepository;
  private final SongRepository songRepository;

  @Autowired
  public SetListItemService(
      ConcertRepository concertRepository,
      SetListItemRepository setListItemRepository,
      SongRepository songRepository
  ) {
    this.concertRepository = concertRepository;
    this.setListItemRepository = setListItemRepository;
    this.songRepository = songRepository;
  }

  public SetListItemDTO createSetListItem(SetListItemPostDTO setListItemPostDTO) {
    Concert concert = this.concertRepository
        .findById(setListItemPostDTO.getConcertId())
        .orElseThrow(() -> new ResourceDoesNotExistException(
            "concert", setListItemPostDTO.getConcertId()));
    Optional<Song> song = Optional.ofNullable(setListItemPostDTO.getSongId())
        .map(id -> this.songRepository.findById(setListItemPostDTO.getSongId())
            .orElseThrow(() -> new ResourceDoesNotExistException("song", id)));
    SetListItem setListItem = new SetListItem(
        concert,
        song.orElse(null),
        setListItemPostDTO.getTitle(),
        setListItemPostDTO.getLength(),
        setListItemPostDTO.getSetIndex() // TODO: handle indexing
    );
    return new SetListItemDTO(this.setListItemRepository.save(setListItem));
  }

  public SetListItemDTO getSetListItemById(Long id) {
    SetListItem setListItem = this.setListItemRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("setListItem"));
    return new SetListItemDTO(setListItem);
  }

  public List<SetListItemDTO> getAllSetListItems() {
    return this.setListItemRepository.findAll().stream().map(SetListItemDTO::new).toList();
  }

  public SetListItemDTO updateSetListItem(Long id, SetListItemPutDTO putDTO) {
    SetListItem setListItem = this.setListItemRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("setListItem"));

    // concert
    EntityUtils.updateNonNullEntityValue(
        () -> this.concertRepository
            .findById(putDTO.getConcertId())
            .orElseThrow(() -> new ResourceDoesNotExistException(
                "concert", putDTO.getConcertId())),
        setListItem::setConcert
    );

    // song
    EntityUtils.updateNonNullEntityValue(
        () -> this.songRepository
            .findById(putDTO.getSongId())
            .orElseThrow(() -> new ResourceDoesNotExistException(
                "song", putDTO.getSongId())),
        setListItem::setSong
    );

    EntityUtils.updateNonBlankStringValue(putDTO::getTitle, setListItem::setTitle);

    // length
    // TODO: enforce positive
    EntityUtils.updateNonNullEntityValue(putDTO::getLength, setListItem::setLength);

    // set index
    // TODO: enforce ordering
    EntityUtils.updateNonNullEntityValue(putDTO::getSetIndex, setListItem::setSetIndex);

    return new SetListItemDTO(this.setListItemRepository.save(setListItem));
  }

  public void deleteSetListItem(Long id) {
    SetListItem setListItem = this.setListItemRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("setListItem"));
    this.setListItemRepository.delete(setListItem);
  }
}
