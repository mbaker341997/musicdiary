package com.kinnock.musicdiary.concert.entity;

import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.loggable.entity.Loggable;
import com.kinnock.musicdiary.setListItem.entity.SetListItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Concert extends Loggable {
  private LocalDate date;
  // TODO: have this be in a DB somewhere?
  private String venue;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "concert")
  private List<SetListItem> setListItems;

  public Concert() {

  }

  public Concert(
      DiaryUser submittedBy,
      List<Artist> artists,
      String title,
      LocalDate date,
      String venue,
      List<SetListItem> setListItems
  ) {
    super(submittedBy, artists, title);
    this.date = date;
    this.venue = venue;
    this.setListItems = setListItems;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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
