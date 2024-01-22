package com.kinnock.musicdiary.concert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.concert.dto.ConcertDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPostDTO;
import com.kinnock.musicdiary.concert.dto.ConcertPutDTO;
import com.kinnock.musicdiary.concert.entity.Concert;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.setlistitem.SetListItemRepository;
import com.kinnock.musicdiary.setlistitem.dto.SetListItemDTO;
import com.kinnock.musicdiary.setlistitem.entity.SetListItem;
import com.kinnock.musicdiary.testutils.BaseControllerTest;
import com.kinnock.musicdiary.testutils.EndpointTest;
import com.kinnock.musicdiary.utils.exception.ResourceDoesNotExistException;
import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerTests extends BaseControllerTest {
  private static final String ENDPOINT = "/api/v1/concerts";

  @Autowired
  private DiaryUserRepository diaryUserRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private ConcertRepository concertRepository;
  @Autowired
  private SetListItemRepository setListItemRepository;

  private static DiaryUser testUser;
  private static ConcertPostDTO testPostDtoBase;
  private static Artist testArtist1;
  private static Artist testArtist2;
  private static boolean initialized = false;

  @BeforeEach
  public void beforeEach() {
    if (!initialized) {
      testUser = this.diaryUserRepository.save(new DiaryUser(
          "testUser",
          "test@fake.com",
          true,
          LocalDate.parse("2023-05-12"),
          "fake bio",
          "fake url")
      );

      testArtist1 = new Artist("ConcertArtist", LocalDate.parse("1973-09-14"), "fakeurl", "fake bio");
      testArtist2 = new Artist("ConcertArtist2", LocalDate.parse("1965-09-14"), "fakeurl2", "fake bio 2");
      this.artistRepository.saveAll(List.of(testArtist1, testArtist2));

      testPostDtoBase = new ConcertPostDTO(
          testUser.getId(),
          List.of(testArtist1.getId()),
          "test concert title",
          LocalDate.parse("2023-11-12"),
          "Alamodome"
      );

      initialized = true;
    }
  }

  @Test
  public void testConcert_HappyPath() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(testPostDtoBase)
            .build()
    );

    Concert concert = this.concertRepository
        .findFirstByTitle(testPostDtoBase.getTitle())
        .orElseThrow(() -> new AssertionFailedError("concert not found in database"));
    ConcertDTO concertDTO = new ConcertDTO(
        concert.getId(),
        concert.getSubmittedBy().getId(),
        List.of(testArtist1.getId()),
        concert.getTitle(),
        concert.getConcertDate(),
        concert.getVenue(),
        List.of()
    );

    // GET read concert
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + concert.getId()),
            status().isOk()
        ).setResponseBody(concertDTO).build()
    );

    // PUT the concert
    ConcertPutDTO concertPutDTO = new ConcertPutDTO(
        List.of(testArtist2.getId()),
        "title2",
        LocalDate.parse("2023-11-22"),
        "Staples Center"
    );
    ConcertDTO updatedDTO = new ConcertDTO(
        concertDTO.getId(),
        testUser.getId(),
        concertPutDTO.getArtistIds(),
        concertPutDTO.getTitle(),
        concertPutDTO.getConcertDate(),
        concertPutDTO.getVenue(),
        List.of()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + concertDTO.getId()),
            status().isOk()
        ).setRequestBody(concertPutDTO).setResponseBody(updatedDTO).build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + concertDTO.getId()),
            status().isOk()
        ).setResponseBody(updatedDTO).build()
    );

    // create a set list item for the concert
    SetListItem setListItem = new SetListItem(
        concert,
        "set list item",
        12,
        1
    );
    this.setListItemRepository.save(setListItem);
    ConcertDTO updatedDTOWithSetListItem = new ConcertDTO(
        concertDTO.getId(),
        testUser.getId(),
        concertPutDTO.getArtistIds(),
        concertPutDTO.getTitle(),
        concertPutDTO.getConcertDate(),
        concertPutDTO.getVenue(),
        List.of(new SetListItemDTO(
            setListItem.getId(),
            concert.getId(),
            null,
            setListItem.getTitle(),
            setListItem.getLength(),
            setListItem.getSetIndex()
        ))
    );
    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + concertDTO.getId()),
            status().isOk()
        ).setResponseBody(updatedDTOWithSetListItem).build()
    );


    // delete the concert
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/" + concertDTO.getId()),
            status().isOk()
        ).build()
    );

    // get the deleted and no result
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + concertDTO.getId()),
            status().isNotFound()
        ).setException(new ResourceNotFoundException("concert")).build()
    );
  }

  @Test
  public void testConcert_UpdateNonExisting() throws Exception {
    ConcertPutDTO concertPutDTO = new ConcertPutDTO(
        List.of(testArtist2.getId()),
        "title2",
        LocalDate.parse("2023-11-22"),
        "venue"
    );
    this.runTest(
        new EndpointTest.Builder(put(ENDPOINT + "/9999"), status().isNotFound())
            .setRequestBody(concertPutDTO)
            .setException(new ResourceNotFoundException("concert"))
            .build()
    );
  }

  @Test
  public void testConcert_DeleteNonExisting() throws Exception {
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/9999"),
            status().isNotFound()
        ).setException(new ResourceNotFoundException("concert")).build()
    );
  }

  @Test
  public void testConcert_UpdateToNonExistentArtist() throws Exception {
    // create a concert
    Concert concert = new Concert(
        testUser,
        Set.of(testArtist1),
        "Update To Non Existent Test",
        testPostDtoBase.getConcertDate(),
        testPostDtoBase.getVenue(),
        List.of()
    );
    this.concertRepository.save(concert);

    ConcertPutDTO concertPutDTO = new ConcertPutDTO(
        List.of(testArtist2.getId(), 700L, 9999L), // artist doesn't exist
        "title2",
        LocalDate.parse("2023-11-22"),
        "venue"
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + concert.getId()),
            status().isUnprocessableEntity())
            .setRequestBody(concertPutDTO)
            .setException(new ResourceDoesNotExistException("artist", List.of(700L, 9999L)))
            .build()
    );
  }

  @Test
  public void testConcert_CreateWithNonExistingEntities() throws Exception {
    // diary user doesn't exist
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isUnprocessableEntity())
            .setRequestBody(new ConcertPostDTO(
                9999L,
                List.of(testArtist1.getId()),
                "test concert title",
                LocalDate.parse("2023-11-12"),
                "Alamodome"
            ))
            .setException(new ResourceDoesNotExistException("diaryUser", 9999L))
            .build()
    );

    // artist doesn't exist
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isUnprocessableEntity())
            .setRequestBody(new ConcertPostDTO(
                testUser.getId(),
                List.of(testArtist1.getId(), 700L, 9999L),
                "test concert title",
                LocalDate.parse("2023-11-12"),
                "Alamodome"
            ))
            .setException(new ResourceDoesNotExistException("artist", List.of(700L, 9999L)))
            .build()
    );
  }
}
