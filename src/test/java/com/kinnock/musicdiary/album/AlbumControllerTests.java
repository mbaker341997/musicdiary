package com.kinnock.musicdiary.album;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.album.dto.AlbumDTO;
import com.kinnock.musicdiary.album.dto.AlbumPostDTO;
import com.kinnock.musicdiary.album.dto.AlbumPutDTO;
import com.kinnock.musicdiary.album.entity.Album;
import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.testutils.BaseControllerTest;
import com.kinnock.musicdiary.testutils.EndpointTest;
import java.time.LocalDate;
import java.util.List;
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
public class AlbumControllerTests extends BaseControllerTest  {
  private static final String ENDPOINT = "/api/v1/albums";

  @Autowired
  private DiaryUserRepository diaryUserRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private AlbumRepository albumRepository;

  private static DiaryUser testUser;
  private static AlbumPostDTO testPostDtoBase;
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

      testArtist1 = new Artist("AlbumArtist", LocalDate.parse("1973-09-14"), "fakeurl", "fake bio");
      testArtist2 = new Artist("AlbumArtist2", LocalDate.parse("1965-09-14"), "fakeurl2", "fake bio 2");
      this.artistRepository.saveAll(List.of(testArtist1, testArtist2));

      testPostDtoBase = new AlbumPostDTO(
          testUser.getId(),
          List.of(testArtist1.getId()),
          "album title",
          "punk",
          LocalDate.parse("1999-12-31"),
          "coverArtUrl"
      );
      initialized = true;
    }
  }

  @Test
  public void testAlbum_HappyPath() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(testPostDtoBase)
            .build()
    );

    Album album = this.albumRepository
        .findFirstByTitle(testPostDtoBase.getTitle())
        .orElseThrow(() -> new AssertionFailedError("album not found in database"));
    AlbumDTO albumDTO = new AlbumDTO(
        album.getId(),
        album.getSubmittedBy().getId(),
        List.of(testArtist1.getId()),
        album.getTitle(),
        album.getGenre(),
        album.getReleaseDate(),
        album.getCoverArtUrl()
    );

    // GET read album
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + album.getId()),
            status().isOk()
        ).setResponseBody(albumDTO).build()
    );

    // PUT the album
    AlbumPutDTO albumPutDTO = new AlbumPutDTO(
        List.of(testArtist2.getId()),
        "title2",
        "rock",
        LocalDate.parse("2023-11-22"),
        "coverARtUrl2"
    );
    AlbumDTO updatedDTO = new AlbumDTO(
        album.getId(),
        testUser.getId(),
        albumPutDTO.getArtistIds(),
        albumPutDTO.getTitle(),
        albumPutDTO.getGenre(),
        albumPutDTO.getReleaseDate(),
        albumPutDTO.getCoverArtUrl()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + album.getId()),
            status().isOk()
        ).setRequestBody(albumPutDTO).setResponseBody(updatedDTO).build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + album.getId()),
            status().isOk()
        ).setResponseBody(updatedDTO).build()
    );

    // delete the album
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/" + album.getId()),
            status().isOk()
        ).build()
    );

    // get the deleted and no result
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + album.getId()),
            status().isNotFound()
        ).build()
    );
  }

  // TODO: you can add non-happy bath ones later when you've consolidated a CRUD framework
}
