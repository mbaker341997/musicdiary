package com.kinnock.musicdiary.diarylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.diarylist.dto.DiaryListDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryPostDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListEntryPutDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListPostDTO;
import com.kinnock.musicdiary.diarylist.dto.DiaryListPutDTO;
import com.kinnock.musicdiary.diarylist.entity.DiaryList;
import com.kinnock.musicdiary.diarylist.entity.DiaryListEntry;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.loggable.LoggableRepository;
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
public class DiaryListControllerTests extends BaseControllerTest {
  private static final String ENDPOINT_DIARY_LIST = "/api/v1/diary-lists";
  private static final String ENDPOINT_DIARY_LIST_ENTRIES = "/api/v1/diary-lists/entries";

  @Autowired
  private DiaryUserRepository diaryUserRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private LoggableRepository loggableRepository;
  @Autowired
  private DiaryListRepository diaryListRepository;
  @Autowired
  private DiaryListEntryRepository diaryListEntryRepository;

  private static DiaryUser testUser;
  private static DiaryListPostDTO testDiaryListPostDtoBase;
  private static Artist testArtist;
  private static Concert concert1;
  private static Concert concert2;
  private static boolean initialized = false;

  @BeforeEach
  public void beforeEach() {
    if (!initialized) {
      testUser = this.diaryUserRepository.save(new DiaryUser(
          "testUserDiaryList",
          "test@fake.com",
          true,
          LocalDate.parse("2023-05-12"),
          "fake bio",
          "fake url")
      );

      testArtist = new Artist("DiaryListArtist", LocalDate.parse("1973-09-14"), "fakeurl", "fake bio");
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
      this.loggableRepository.saveAll(List.of(concert1, concert2));

      testDiaryListPostDtoBase = new DiaryListPostDTO(
          testUser.getId(),
          "Test List",
          "a short description"
      );

      initialized = true;
    }
  }

  @Test
  public void testDiaryList_HappyPath() throws Exception {
    // DiaryList POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT_DIARY_LIST), status().isOk())
            .setRequestBody(testDiaryListPostDtoBase)
            .build()
    );

    // DiaryList GET
    List<DiaryList> diaryLists = this.diaryListRepository.findByDiaryUserId(testUser.getId());
    assertEquals(1, diaryLists.size());
    DiaryList diaryList = diaryLists.get(0);
    DiaryListDTO diaryListDTO = new DiaryListDTO(
        diaryList.getId(),
        testDiaryListPostDtoBase.getDiaryUserId(),
        testDiaryListPostDtoBase.getTitle(),
        testDiaryListPostDtoBase.getDescription(),
        List.of()
    );
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT_DIARY_LIST + "/" + diaryList.getId()),
            status().isOk()
        ).setResponseBody(diaryListDTO).build()
    );

    // DiaryList PUT
    DiaryListPutDTO diaryListPutDTO = new DiaryListPutDTO(
        "a new title",
        "a new description"
    );
    DiaryListDTO updatedDiaryListDTO = new DiaryListDTO(
        diaryList.getId(),
        testDiaryListPostDtoBase.getDiaryUserId(),
        diaryListPutDTO.getTitle(),
        diaryListPutDTO.getDescription(),
        List.of()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT_DIARY_LIST + "/" + diaryList.getId()),
            status().isOk()
        ).setRequestBody(diaryListPutDTO).setResponseBody(updatedDiaryListDTO).build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT_DIARY_LIST + "/" + diaryList.getId()),
            status().isOk()
        ).setResponseBody(updatedDiaryListDTO).build()
    );

    // DiaryListEntry POST
    DiaryListEntryPostDTO diaryListEntryPostDTO = new DiaryListEntryPostDTO(
        diaryList.getId(),
        concert1.getId(),
        1,
        "a note"
    );

    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT_DIARY_LIST_ENTRIES), status().isOk())
            .setRequestBody(diaryListEntryPostDTO)
            .build()
    );

    // DiaryListEntry GET
    List<DiaryListEntry> diaryListEntries = this.diaryListEntryRepository
        .findByDiaryListId(diaryList.getId());
    assertEquals(1, diaryListEntries.size());
    DiaryListEntry diaryListEntry = diaryListEntries.get(0);
    DiaryListEntryDTO diaryListEntryDTO = new DiaryListEntryDTO(
        diaryListEntry.getId(),
        diaryListEntryPostDTO.getDiaryListId(),
        diaryListEntryPostDTO.getLoggableId(),
        diaryListEntryPostDTO.getListIndex(),
        diaryListEntryPostDTO.getNote()
    );
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT_DIARY_LIST_ENTRIES + "/" + diaryListEntry.getId()),
            status().isOk()
        ).setResponseBody(diaryListEntryDTO).build()
    );

    // DiaryListEntry PUT
    DiaryListEntryPutDTO diaryListEntryPutDTO = new DiaryListEntryPutDTO(
        concert2.getId(),
        2,
        "a new note"
    );
    DiaryListEntryDTO updatedDiaryListEntryDTO = new DiaryListEntryDTO(
        diaryListEntry.getId(),
        diaryListEntryPostDTO.getDiaryListId(),
        diaryListEntryPutDTO.getLoggableId(),
        diaryListEntryPutDTO.getListIndex(),
        diaryListEntryPutDTO.getNote()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT_DIARY_LIST_ENTRIES + "/" + diaryListEntry.getId()),
            status().isOk()
        ).setRequestBody(diaryListEntryPutDTO).setResponseBody(updatedDiaryListEntryDTO).build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT_DIARY_LIST_ENTRIES + "/" + diaryListEntry.getId()),
            status().isOk()
        ).setResponseBody(updatedDiaryListEntryDTO).build()
    );

    // DiaryList GET
    DiaryListDTO diaryListWithEntryDTO = new DiaryListDTO(
        diaryList.getId(),
        testDiaryListPostDtoBase.getDiaryUserId(),
        diaryListPutDTO.getTitle(),
        diaryListPutDTO.getDescription(),
        List.of(updatedDiaryListEntryDTO)
    );
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT_DIARY_LIST + "/" + diaryList.getId()),
            status().isOk()
        ).setResponseBody(diaryListWithEntryDTO).build()
    );

    // DiaryListEntry DELETE
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT_DIARY_LIST_ENTRIES + "/" + diaryListEntry.getId()),
            status().isOk()
        ).build()
    );

    // get deleted DiaryListEntry and no results
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT_DIARY_LIST_ENTRIES + "/" + diaryListEntry.getId()),
            status().isNotFound()
        ).build()
    );

    // DiaryList GET no more entry
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT_DIARY_LIST + "/" + diaryList.getId()),
            status().isOk()
        ).setResponseBody(updatedDiaryListDTO).build()
    );

    // DiaryList DELETE
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT_DIARY_LIST + "/" + diaryList.getId()),
            status().isOk()
        ).build()
    );

    // GET deleted DiaryList and no result
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT_DIARY_LIST + "/" + diaryList.getId()),
            status().isNotFound()
        ).build()
    );
  }

  // TODO: unhappy case tests
}
