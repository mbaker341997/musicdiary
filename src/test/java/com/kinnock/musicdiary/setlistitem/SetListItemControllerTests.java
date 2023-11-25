package com.kinnock.musicdiary.setlistitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.concert.ConcertRepository;
import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemDTO;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemPostDTO;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemPutDTO;
import com.kinnock.musicdiary.setlistitem.entity.SetListItem;
import com.kinnock.musicdiary.song.SongRepository;
import com.kinnock.musicdiary.song.entity.Song;
import com.kinnock.musicdiary.testutils.BaseControllerTest;
import com.kinnock.musicdiary.testutils.EndpointTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class SetListItemControllerTests extends BaseControllerTest {
  private static final String ENDPOINT = "/api/v1/set-list-items";

  @Autowired
  private DiaryUserRepository diaryUserRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private ConcertRepository concertRepository;
  @Autowired
  private SetListItemRepository setListItemRepository;
  @Autowired
  private SongRepository songRepository;

  private static SetListItemPostDTO testPostDtoBase;
  private static Concert concert1;
  private static Concert concert2;
  private static Song song;
  private static boolean initialized = false;

  @BeforeEach
  public void beforeEach() {
    if (!initialized) {
      DiaryUser testUser = this.diaryUserRepository.save(new DiaryUser(
          "testUser",
          "test@fake.com",
          true,
          LocalDate.parse("2023-05-12"),
          "fake bio",
          "fake url")
      );

      Artist testArtist = new Artist(
          "SetListItemArtist",
          LocalDate.parse("1973-09-14"),
          "fakeurl",
          "fake bio"
      );
      this.artistRepository.save(testArtist);

      concert1 = new Concert(
          testUser,
          Set.of(testArtist),
          "set list test concert",
          LocalDate.parse("2023-11-25"),
          "Some backyard",
          List.of()
      );
      concert2 = new Concert(
          testUser,
          Set.of(testArtist),
          "set list test concert 2",
          LocalDate.parse("2023-11-25"),
          "Some backyard",
          List.of()
      );
      this.concertRepository.saveAll(List.of(concert1, concert2));


      song = new Song(
          testUser,
          Set.of(testArtist),
          "song title",
          12,
          "lyricUrl"
      );
      this.songRepository.save(song);

      testPostDtoBase = new SetListItemPostDTO(
          concert1.getId(),
          null,
          "set list item title",
          12,
          1
      );

      initialized = true;
    }
  }

  @Test
  public void testSetListItem_HappyPath() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(testPostDtoBase)
            .build()
    );

    List<SetListItem> setListItems = this.setListItemRepository
        .findByConcertId(testPostDtoBase.getConcertId());
    assertEquals(1, setListItems.size());
    SetListItem setListItem = setListItems.get(0);
    SetListItemDTO setListItemDTO = new SetListItemDTO(
        setListItem.getId(),
        testPostDtoBase.getConcertId(),
        testPostDtoBase.getSongId(),
        testPostDtoBase.getTitle(),
        testPostDtoBase.getLength(),
        testPostDtoBase.getSetIndex()
    );

    // GET read concert
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + setListItem.getId()),
            status().isOk()
        ).setResponseBody(setListItemDTO).build()
    );

    // PUT the album
    SetListItemPutDTO setListItemPutDTO = new SetListItemPutDTO(
        concert2.getId(),
        song.getId(),
        "updated set list item title",
        155,
        2
    );
    SetListItemDTO updatedDTO = new SetListItemDTO(
        setListItem.getId(),
        setListItemPutDTO.getConcertId(),
        setListItemPutDTO.getSongId(),
        setListItemPutDTO.getTitle(),
        setListItemPutDTO.getLength(),
        setListItemPutDTO.getSetIndex()
    );

    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + setListItemDTO.getId()),
            status().isOk()
        ).setRequestBody(setListItemPutDTO).setResponseBody(updatedDTO).build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + setListItemDTO.getId()),
            status().isOk()
        ).setResponseBody(updatedDTO).build()
    );


    // delete the album
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/" + setListItemDTO.getId()),
            status().isOk()
        ).build()
    );

    // get the deleted and no result
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + setListItemDTO.getId()),
            status().isNotFound()
        ).build()
    );
  }
}
