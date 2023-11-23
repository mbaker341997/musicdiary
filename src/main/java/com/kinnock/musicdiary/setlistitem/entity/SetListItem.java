package com.kinnock.musicdiary.setlistitem.entity;

import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.song.entity.Song;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class SetListItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_id", nullable = false)
  private Concert concert;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "song_id", nullable = false)
  private Song song;

  private Integer length;

  private Integer setIndex;

  public SetListItem() {

  }

  public SetListItem(
      Concert concert,
      Song song,
      Integer length,
      Integer setIndex
  ) {
    this.concert = concert;
    this.song = song;
    this.length = length;
    this.setIndex = setIndex;
  }

  public Long getId() {
    return id;
  }

  public Concert getConcert() {
    return concert;
  }

  public void setConcert(Concert concert) {
    this.concert = concert;
  }

  public Song getSong() {
    return song;
  }

  public void setSong(Song song) {
    this.song = song;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Integer getSetIndex() {
    return setIndex;
  }

  public void setSetIndex(Integer setIndex) {
    this.setIndex = setIndex;
  }
}
