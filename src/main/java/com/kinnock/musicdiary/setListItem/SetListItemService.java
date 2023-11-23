package com.kinnock.musicdiary.setListItem;

import com.kinnock.musicdiary.concert.ConcertRepository;
import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.setListItem.dto.SetListItemDTO;
import com.kinnock.musicdiary.setListItem.dto.SetListItemPostDTO;
import com.kinnock.musicdiary.setListItem.dto.SetListItemPutDTO;
import com.kinnock.musicdiary.setListItem.entity.SetListItem;
import com.kinnock.musicdiary.song.SongRepository;
import com.kinnock.musicdiary.song.entity.Song;
import com.kinnock.musicdiary.utils.EntityUtils;
import java.util.List;
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
        .orElseThrow(() -> new IllegalStateException("concert not found")); // TODO: 404
    Song song = this.songRepository.findById(setListItemPostDTO.getSongId()).orElseThrow(); // TODO: 404
    SetListItem setListItem = new SetListItem(
        concert,
        song,
        setListItemPostDTO.getLength(),
        setListItemPostDTO.getSetIndex() // TODO: handle indexing
    );
    return new SetListItemDTO(setListItem);
  }

  public SetListItemDTO getSetListItemById(Long id) {
    SetListItem setListItem = this.setListItemRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("set list item not found"));
    return new SetListItemDTO(setListItem);
  }

  public List<SetListItemDTO> getAllSetListItems() {
    return this.setListItemRepository.findAll().stream().map(SetListItemDTO::new).toList();
  }

  public SetListItemDTO updateSetListItem(Long id, SetListItemPutDTO putDTO) {
    SetListItem setListItem = this.setListItemRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("set list item not found"));

    // concert
    EntityUtils.updateNonNullEntityValue(
        () -> this.concertRepository
            .findById(putDTO.getConcertId())
            .orElseThrow(() -> new IllegalStateException("concert not found")),
        setListItem::setConcert
    );

    // song
    EntityUtils.updateNonNullEntityValue(
        () -> this.songRepository
            .findById(putDTO.getSongId())
            .orElseThrow(() -> new IllegalStateException("song not found")),
        setListItem::setSong
    );

    // length
    // TODO: enforce positive
    EntityUtils.updateNonNullEntityValue(putDTO::getLength, setListItem::setLength);

    // set index
    // TODO: enforce ordering
    EntityUtils.updateNonNullEntityValue(setListItem::getSetIndex, setListItem::setSetIndex);

    return new SetListItemDTO(setListItem);
  }

  public SetListItemDTO deleteSetListItem(Long id) {
    SetListItem setListItem = this.setListItemRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("set list item not found"));
    this.setListItemRepository.delete(setListItem);
    return new SetListItemDTO(setListItem);
  }
}
