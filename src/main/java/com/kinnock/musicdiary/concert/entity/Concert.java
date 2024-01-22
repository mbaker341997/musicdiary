package com.kinnock.musicdiary.concert.entity;

import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.loggable.entity.Loggable;
import com.kinnock.musicdiary.setlistitem.entity.SetListItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Concert extends Loggable {
  private LocalDate concertDate;
  // TODO: have this be in a DB somewhere?
  private String venue;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "concert")
  private List<SetListItem> setListItems;

  public Concert() {

  }

  public Concert(
      DiaryUser submittedBy,
      Set<Artist> artists,
      String title,
      LocalDate concertDate,
      String venue,
      List<SetListItem> setListItems
  ) {
    super(submittedBy, artists, title);
    this.concertDate = concertDate;
    this.venue = venue;
    this.setListItems = setListItems;
  }

  public LocalDate getConcertDate() {
    return concertDate;
  }

  public void setConcertDate(LocalDate concertDate) {
    this.concertDate = concertDate;
  }

  public String getVenue() {
    return venue;
  }

  public void setVenue(String venue) {
    this.venue = venue;
  }

  public List<SetListItem> getSetListItems() {
    return setListItems;
  }

  public void setSetListItems(List<SetListItem> setListItems) {
    this.setListItems = setListItems;
  }
}
