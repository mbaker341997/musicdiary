package com.kinnock.musicdiary.artist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.artist.dto.ArtistDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPostDTO;
import com.kinnock.musicdiary.artist.dto.ArtistPutDTO;
import com.kinnock.musicdiary.artist.entity.Artist;
import com.kinnock.musicdiary.testutils.BaseControllerTest;
import com.kinnock.musicdiary.testutils.EndpointTest;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class ArtistControllerTests extends BaseControllerTest {
  private static final String ENDPOINT = "/api/v1/artist";

  private static final ArtistPostDTO ARTIST_POST_DTO_BASE = new ArtistPostDTO(
      "Nas",
      LocalDate.of(1973, Month.SEPTEMBER, 14),
      "https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Nas_%2852381849239%29_%28cropped%29.jpg/1280px-Nas_%2852381849239%29_%28cropped%29.jpg",
      "Rapper from Queensbridge"
  );


  @Autowired
  private ArtistRepository artistRepository;

  @AfterEach
  public void cleanUp() {
    this.artistRepository.deleteAll();
  }

  // TODO: DRY out the updates tests?
  // TODO: negative case tests
  @Test
  public void testArtist_HappyPath() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(ARTIST_POST_DTO_BASE)
            .build()
    );

    Artist artistEntity = artistRepository
        .findByName(ARTIST_POST_DTO_BASE.getName())
        .orElseThrow(() -> new AssertionFailedError("artist not found in database"));
    ArtistDTO artistDTO = new ArtistDTO(artistEntity);

    // GET read artist
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(artistDTO).build()
    );

    // PUT the artist
    ArtistPutDTO putDTO = new ArtistPutDTO(
        "New Name Not Nas",
        LocalDate.of(2007, Month.OCTOBER, 30),
        "https://fake.com/fake",
        "Everything is changed"
    );
    ArtistDTO updatedResponseDTO = new ArtistDTO(
        artistEntity.getId(),
        putDTO.getName(),
        putDTO.getDateOfBirth(),
        putDTO.getPictureUrl(),
        putDTO.getBio()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + artistEntity.getId()), status().isOk())
            .setRequestBody(putDTO)
            .setResponseBody(updatedResponseDTO)
            .build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(updatedResponseDTO).build()
    );

    // delete the user
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(updatedResponseDTO).build()
    );

    // GET the deleted and no result
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isNotFound()
        ).build()
    );
  }

  @Test
  public void testArtist_UpdateName() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(ARTIST_POST_DTO_BASE)
            .build()
    );

    Artist artistEntity = artistRepository
        .findByName(ARTIST_POST_DTO_BASE.getName())
        .orElseThrow(() -> new AssertionFailedError("artist not found in database"));
    ArtistDTO artistDTO = new ArtistDTO(artistEntity);

    // GET read a user
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(artistDTO).build()
    );

    // PUT the user
    ArtistPutDTO putDTO = new ArtistPutDTO(
        "New Name",
        null,
        null,
        null
    );
    ArtistDTO updatedResponseDTO = new ArtistDTO(
        artistEntity.getId(),
        putDTO.getName(),
        artistEntity.getDateOfBirth(),
        artistEntity.getPictureUrl(),
        artistEntity.getBio()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + artistEntity.getId()), status().isOk())
            .setRequestBody(putDTO)
            .setResponseBody(updatedResponseDTO)
            .build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(updatedResponseDTO).build()
    );
  }

  @Test
  public void testArtist_UpdateDOB() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(ARTIST_POST_DTO_BASE)
            .build()
    );

    Artist artistEntity = artistRepository
        .findByName(ARTIST_POST_DTO_BASE.getName())
        .orElseThrow(() -> new AssertionFailedError("artist not found in database"));
    ArtistDTO artistDTO = new ArtistDTO(artistEntity);

    // GET read a user
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(artistDTO).build()
    );

    // PUT the user
    ArtistPutDTO putDTO = new ArtistPutDTO(
        null,
        LocalDate.of(1973, Month.SEPTEMBER, 15),
        null,
        null
    );
    ArtistDTO updatedResponseDTO = new ArtistDTO(
        artistEntity.getId(),
        artistEntity.getName(),
        putDTO.getDateOfBirth(),
        artistEntity.getPictureUrl(),
        artistEntity.getBio()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + artistEntity.getId()), status().isOk())
            .setRequestBody(putDTO)
            .setResponseBody(updatedResponseDTO)
            .build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(updatedResponseDTO).build()
    );
  }

  @Test
  public void testArtist_UpdateBio() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(ARTIST_POST_DTO_BASE)
            .build()
    );

    Artist artistEntity = artistRepository
        .findByName(ARTIST_POST_DTO_BASE.getName())
        .orElseThrow(() -> new AssertionFailedError("artist not found in database"));
    ArtistDTO artistDTO = new ArtistDTO(artistEntity);

    // GET read a user
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(artistDTO).build()
    );

    // PUT the user
    ArtistPutDTO putDTO = new ArtistPutDTO(
        null,
        null,
        null,
        "new bio"
    );
    ArtistDTO updatedResponseDTO = new ArtistDTO(
        artistEntity.getId(),
        artistEntity.getName(),
        artistEntity.getDateOfBirth(),
        artistEntity.getPictureUrl(),
        putDTO.getBio()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + artistEntity.getId()), status().isOk())
            .setRequestBody(putDTO)
            .setResponseBody(updatedResponseDTO)
            .build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(updatedResponseDTO).build()
    );
  }
  @Test
  public void testArtist_UpdateURL() throws Exception {
    // POST
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(ARTIST_POST_DTO_BASE)
            .build()
    );

    Artist artistEntity = artistRepository
        .findByName(ARTIST_POST_DTO_BASE.getName())
        .orElseThrow(() -> new AssertionFailedError("artist not found in database"));
    ArtistDTO artistDTO = new ArtistDTO(artistEntity);

    // GET read a user
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(artistDTO).build()
    );

    // PUT the user
    ArtistPutDTO putDTO = new ArtistPutDTO(
        null,
        null,
        "https://www.fake.com/fakepic.jpg",
        null
    );
    ArtistDTO updatedResponseDTO = new ArtistDTO(
        artistEntity.getId(),
        artistEntity.getName(),
        artistEntity.getDateOfBirth(),
        putDTO.getPictureUrl(),
        artistEntity.getBio()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + artistEntity.getId()), status().isOk())
            .setRequestBody(putDTO)
            .setResponseBody(updatedResponseDTO)
            .build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + artistEntity.getId()),
            status().isOk()
        ).setResponseBody(updatedResponseDTO).build()
    );
  }
}
