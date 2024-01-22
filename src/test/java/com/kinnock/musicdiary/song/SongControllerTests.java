package com.kinnock.musicdiary.song;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.album.AlbumRepository;
import com.kinnock.musicdiary.album.entity.Album;
import com.kinnock.musicdiary.artist.ArtistRepository;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.diaryuser.DiaryUserRepository;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.song.dto.SongDTO;
import com.kinnock.musicdiary.song.dto.SongPostDTO;
import com.kinnock.musicdiary.song.dto.SongPutDTO;
import com.kinnock.musicdiary.song.entity.Song;
import com.kinnock.musicdiary.testutils.BaseControllerTest;
import com.kinnock.musicdiary.testutils.EndpointTest;
import com.kinnock.musicdiary.utils.exception.ResourceDoesNotExistException;
import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
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
public class SongControllerTests extends BaseControllerTest {
  private static final String ENDPOINT = "/api/v1/songs";

  @Autowired
  private DiaryUserRepository diaryUserRepository;
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private SongRepository songRepository;

  private static DiaryUser testUser;
  private static Album testAlbum1;
  private static Album testAlbum2;
  private static SongPostDTO testPostDtoBase;
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

      testArtist1 = new Artist("SongArtist", LocalDate.parse("1973-09-14"), "fakeurl", "fake bio");
      testArtist2 = new Artist("SongArtist2", LocalDate.parse("1965-09-14"), "fakeurl2", "fake bio 2");
      this.artistRepository.saveAll(List.of(testArtist1, testArtist2));

      testAlbum1 = this.albumRepository.save(new Album(
          testUser,
          Set.of(testArtist1),
          "album title for song tests",
          "punk",
          LocalDate.parse("1999-12-31"),
          "coverArtUrl"
      ));
      testAlbum2 = this.albumRepository.save(new Album(
          testUser,
          Set.of(testArtist1),
          "album title for song tests 2",
          "pop",
          LocalDate.parse("1998-11-14"),
          "coverArtUrl2"
      ));

      testPostDtoBase = new SongPostDTO(
          testUser.getId(),
          List.of(testArtist1.getId()),
          "song title",
          testAlbum1.getId(),
          12,
          "lyricsUrl",
          1
      );
      initialized = true;
    }
  }

  @AfterEach
  public void cleanup() {
    this.songRepository.deleteAll();
  }

  @Test
  public void testSong_HappyPath() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(testPostDtoBase)
            .build()
    );

    Song song = this.songRepository
        .findFirstByTitle(testPostDtoBase.getTitle())
        .orElseThrow(() -> new AssertionFailedError("song not found in database"));
    SongDTO songDTO = new SongDTO(
        song.getId(),
        testUser.getId(),
        List.of(testArtist1.getId()),
        song.getTitle(),
        testAlbum1.getId(),
        song.getLength(),
        song.getLyricsUrl(),
        song.getAlbumIndex()
    );

    // GET read song
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + song.getId()),
            status().isOk()
        ).setResponseBody(songDTO).build()
    );

    // PUT the song
    SongPutDTO songPutDTO = new SongPutDTO(
        List.of(testArtist2.getId()),
        "title2",
        testAlbum2.getId(),
        89,
        "lyricsUrl22",
        2
    );
    SongDTO updatedDTO = new SongDTO(
        song.getId(),
        song.getSubmittedBy().getId(),
        List.of(testArtist2.getId()),
        songPutDTO.getTitle(),
        testAlbum2.getId(),
        songPutDTO.getLength(),
        songPutDTO.getLyricsUrl(),
        songPutDTO.getAlbumIndex()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + song.getId()),
            status().isOk()
        ).setRequestBody(songPutDTO).setResponseBody(updatedDTO).build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + song.getId()),
            status().isOk()
        ).setResponseBody(updatedDTO).build()
    );

    // delete the song
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/" + song.getId()),
            status().isOk()
        ).build()
    );

    // get the deleted and no result
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + song.getId()),
            status().isNotFound()
        ).setException(new ResourceNotFoundException("song")).build()
    );
  }

  @Test
  public void testSong_CreateWithNonExistingEntities() throws Exception {
    // diary user doesn't exist
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isUnprocessableEntity())
            .setRequestBody(new SongPostDTO(
                9999L,
                List.of(testArtist1.getId()),
                "song title",
                testAlbum1.getId(),
                12,
                "lyricsUrl",
                1
            ))
            .setException(new ResourceDoesNotExistException("diaryUser", 9999L))
            .build()
    );

    // artist doesn't exist
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isUnprocessableEntity())
            .setRequestBody(new SongPostDTO(
                testUser.getId(),
                List.of(testArtist1.getId(), 700L, 9999L),
                "song title",
                testAlbum1.getId(),
                12,
                "lyricsUrl",
                1
            ))
            .setException(new ResourceDoesNotExistException("artist", List.of(700L, 9999L)))
            .build()
    );

    // album doesn't exist
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isUnprocessableEntity())
            .setRequestBody(new SongPostDTO(
                testUser.getId(),
                List.of(testArtist1.getId()),
                "song title",
                9999L,
                12,
                "lyricsUrl",
                1
            ))
            .setException(new ResourceDoesNotExistException("album", 9999L))
            .build()
    );
  }

  @Test
  public void testSong_UpdateNonExistingAttributes() throws Exception {
    // create a song
    Song song = new Song(
        testUser,
        Set.of(testArtist1),
        "Update To Non Existent Test",
        12,
        "lyricsUrl"
    );
    this.songRepository.save(song);

    // artist doesn't exist
    SongPutDTO songPutDTO = new SongPutDTO(
        List.of(testArtist2.getId(), 700L, 9999L),
        "title2",
        testAlbum2.getId(),
        89,
        "lyricsUrl22",
        2
    );
    this.runTest(
        new EndpointTest.Builder(put(ENDPOINT + "/" + song.getId()),
            status().isUnprocessableEntity())
            .setRequestBody(songPutDTO)
            .setException(new ResourceDoesNotExistException("artist", List.of(700L, 9999L)))
            .build()
    );

    // album doesn't exist
    SongPutDTO songPutDTO2 = new SongPutDTO(
        List.of(testArtist2.getId()),
        "title2",
        9999L,
        89,
        "lyricsUrl22",
        2
    );
    this.runTest(
        new EndpointTest.Builder(put(ENDPOINT + "/" + song.getId()),
            status().isUnprocessableEntity())
            .setRequestBody(songPutDTO2)
            .setException(new ResourceDoesNotExistException("album", 9999L))
            .build()
    );
  }

  @Test
  public void testSong_UpdateNonExisting() throws Exception {
    SongPutDTO songPutDTO = new SongPutDTO(
        List.of(testArtist2.getId()),
        "title2",
        testAlbum2.getId(),
        89,
        "lyricsUrl22",
        2
    );
    this.runTest(
        new EndpointTest.Builder(put(ENDPOINT + "/9999"), status().isNotFound())
            .setRequestBody(songPutDTO)
            .setException(new ResourceNotFoundException("song"))
            .build()
    );
  }

  @Test
  public void testSong_DeleteNonExisting() throws Exception {
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/9999"),
            status().isNotFound()
        ).setException(new ResourceNotFoundException("song")).build()
    );
  }
}
