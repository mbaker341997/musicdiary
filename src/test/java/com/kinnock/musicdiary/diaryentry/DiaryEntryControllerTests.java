package com.kinnock.musicdiary.diaryentry;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryDTO;
import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryPostDTO;
import com.kinnock.musicdiary.diaryentry.dto.DiaryEntryPutDTO;
import com.kinnock.musicdiary.diaryentry.entity.DiaryEntry;
import com.kinnock.musicdiary.diaryentry.entity.Rating;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.song.SongRepository;
import com.kinnock.musicdiary.song.entity.Song;
import com.kinnock.musicdiary.testutils.BaseControllerTest;
import com.kinnock.musicdiary.testutils.EndpointTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.kinnock.musicdiary.utils.exception.ResourceDoesNotExistException;
import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class DiaryEntryControllerTests extends BaseControllerTest {
  private static final String ENDPOINT = "/api/v1/diary-entries";

  @Autowired
  private DiaryUserRepository diaryUserRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private DiaryEntryRepository diaryEntryRepository;
  @Autowired
  private SongRepository songRepository;

  private static DiaryEntryPostDTO testPostDtoBase;
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
          "DiaryEntryArtist",
          LocalDate.parse("1973-09-14"),
          "fakeurl",
          "fake bio"
      );
      this.artistRepository.save(testArtist);


      song = new Song(
          testUser,
          Set.of(testArtist),
          "song title for diary entry tests",
          12,
          "lyricUrl"
      );
      this.songRepository.save(song);

      testPostDtoBase = new DiaryEntryPostDTO(
          testUser.getId(),
          song.getId(),
          LocalDate.parse("2023-11-19"),
          Rating.FIVE,
          "a review"
      );

      initialized = true;
    }
  }

  @Test
  public void testDiaryEntry_HappyPath() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(testPostDtoBase)
            .build()
    );

    List<DiaryEntry> diaryEntries = this.diaryEntryRepository
        .findByDiaryUserId(testPostDtoBase.getUserId());
    assertEquals(1, diaryEntries.size());
    DiaryEntry diaryEntry = diaryEntries.get(0);
    DiaryEntryDTO diaryEntryDTO = new DiaryEntryDTO(
        diaryEntry.getId(),
        testPostDtoBase.getUserId(),
        testPostDtoBase.getLoggableId(),
        testPostDtoBase.getLogDate(),
        testPostDtoBase.getRating(),
        testPostDtoBase.getReview()
    );

    // GET read diary entrie
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + diaryEntry.getId()),
            status().isOk()
        ).setResponseBody(diaryEntryDTO).build()
    );

    // PUT the diary entry
    DiaryEntryPutDTO diaryEntryPutDTO = new DiaryEntryPutDTO(
        LocalDate.parse("2023-10-31"),
        Rating.FOUR,
        "I have updated my review"
    );
    DiaryEntryDTO updatedDTO = new DiaryEntryDTO(
        diaryEntry.getId(),
        diaryEntry.getDiaryUser().getId(),
        diaryEntry.getLoggable().getId(),
        diaryEntryPutDTO.getLogDate(),
        diaryEntryPutDTO.getRating(),
        diaryEntryPutDTO.getReview()
    );

    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + diaryEntry.getId()),
            status().isOk()
        ).setRequestBody(diaryEntryPutDTO).setResponseBody(updatedDTO).build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + diaryEntry.getId()),
            status().isOk()
        ).setResponseBody(updatedDTO).build()
    );


    // delete the diary entry
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/" + diaryEntry.getId()),
            status().isOk()
        ).build()
    );

    // get the deleted and no result
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + diaryEntry.getId()),
            status().isNotFound()
        ).setException(new ResourceNotFoundException("diaryEntry")).build()
    );
  }

  // Unhappy case test s
}
