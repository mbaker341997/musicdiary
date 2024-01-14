package com.kinnock.musicdiary.diaryuser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.diaryuser.dto.DiaryUserDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPostDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPutDTO;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.testutils.BaseControllerTest;
import com.kinnock.musicdiary.testutils.EndpointTest;
import java.time.LocalDate;
import java.time.Month;

import com.kinnock.musicdiary.utils.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class DiaryUserControllerTests extends BaseControllerTest {
  
  private static final String ENDPOINT = "/api/v1/users";

  @Autowired
  private DiaryUserRepository diaryUserRepository;

  @Test
  public void testDiaryUser_HappyPath() throws Exception {
    // POST
    DiaryUserPostDTO postDTO = new DiaryUserPostDTO(
        "MichaelFoot1980",
        "foot@fake.com",
        "I was leader of the Labour Party",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Michael_Foot_%281981%29.jpg/1024px-Michael_Foot_%281981%29.jpg",
        false,
        LocalDate.of(1913, Month.JULY, 23)
    );

    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isOk())
            .setRequestBody(postDTO)
            .build()
    );
    
    DiaryUser diaryUserEntity = this.diaryUserRepository
        .findByUsername(postDTO.getUsername())
        .orElseThrow(() -> new AssertionFailedError("diary user not found in database"));
    DiaryUserDTO diaryUserDTO = new DiaryUserDTO(diaryUserEntity);
    
    // GET read a user
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + diaryUserEntity.getId()),
            status().isOk()
        ).setResponseBody(diaryUserDTO).build()
    );

    // PUT the user
    DiaryUserPutDTO putDTO = new DiaryUserPutDTO(
        null,
        null,
        "I only think the bio should be different",
        null,
        null,
        null
    );
    DiaryUserDTO updatedResponseDTO = new DiaryUserDTO(
        diaryUserEntity.getId(),
        diaryUserEntity.getUsername(),
        diaryUserEntity.getEmail(),
        putDTO.getBio(),
        diaryUserEntity.getProfileImageUrl(),
        diaryUserEntity.getIsAdmin(),
        diaryUserEntity.getDateOfBirth()
    );
    this.runTest(
        new EndpointTest.Builder(
            put(ENDPOINT + "/" + diaryUserEntity.getId()), status().isOk())
            .setRequestBody(putDTO)
            .setResponseBody(updatedResponseDTO)
            .build()
    );

    // GET the update
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + diaryUserEntity.getId()),
            status().isOk()
        ).setResponseBody(updatedResponseDTO).build()
    );

    // delete the user
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/" + diaryUserEntity.getId()),
            status().isOk()
        ).build()
    );

    // GET the deleted and no result
    this.runTest(
        new EndpointTest.Builder(
            get(ENDPOINT + "/" + diaryUserEntity.getId()),
            status().isNotFound()
        ).setException(new ResourceNotFoundException("diaryUser")).build()
    );
  }

  @Test
  public void testDiaryUser_AlreadyTaken() throws Exception {
    // create a user
    DiaryUser steel = new DiaryUser(
        "DavidSteel1976",
        "steel@fake.com",
        false,
        LocalDate.of(1938, Month.MARCH, 31),
        "I was leader of the Liberal Party",
        "https://upload.wikimedia.org/wikipedia/commons/b/b9/Official_portrait_of_The_Lord_Steel_of_Aikwood.jpg"
    );
    this.diaryUserRepository.save(steel);

    // try to create one where username already taken
    DiaryUserPostDTO postDTOUsernameTaken = new DiaryUserPostDTO(
        "DavidSteel1976",
        "davidsteel@fake.com",
        "I am not leader of the Liberal Party",
        null,
        false,
        LocalDate.of(1913, Month.JULY, 23)
    );
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isBadRequest())
            .setRequestBody(postDTOUsernameTaken)
            .setException(new BadUserDataException("username taken"))
            .build()
    );

    // try to create one where email already taken
    DiaryUserPostDTO postDTOEmailTaken = new DiaryUserPostDTO(
        "DavidSteel1978",
        "steel@fake.com",
        "I am not leader of the Liberal Party",
        null,
        false,
        LocalDate.of(1913, Month.JULY, 23)
    );
    this.runTest(
        new EndpointTest.Builder(post(ENDPOINT), status().isBadRequest())
            .setRequestBody(postDTOEmailTaken)
            .setException(new BadUserDataException("email taken"))
            .build()
    );

    DiaryUser jenkins = new DiaryUser(
        "RoyJenkins1982",
        "jenkins@fake.com",
        false,
        LocalDate.of(1938, Month.MARCH, 31),
        "I was leader of the Liberal Party",
        "https://upload.wikimedia.org/wikipedia/commons/b/b9/Official_portrait_of_The_Lord_Steel_of_Aikwood.jpg"
    );
    this.diaryUserRepository.save(jenkins);

    // try to update to username already taken
    DiaryUserPutDTO putDTOUsernameTaken = new DiaryUserPutDTO(
        "DavidSteel1976",
        null,
        null,
        null,
        null,
        null
    );
    this.runTest(
        new EndpointTest.Builder(
              put(ENDPOINT + "/" + jenkins.getId()),
              status().isBadRequest())
            .setRequestBody(putDTOUsernameTaken)
            .setException(new BadUserDataException("username taken")).build()
    );
    // try to update to email already taken
    DiaryUserPutDTO putDTOEmailTaken = new DiaryUserPutDTO(
        null,
        "steel@fake.com",
        null,
        null,
        null,
        null
    );
    this.runTest(
        new EndpointTest.Builder(
              put(ENDPOINT + "/" + jenkins.getId()),
              status().isBadRequest())
            .setRequestBody(putDTOEmailTaken)
            .setException(new BadUserDataException("email taken")).build()
    );
  }

  @Test
  public void testDiaryUser_UpdateNonExisting() throws Exception {
    DiaryUserPutDTO putDTO = new DiaryUserPutDTO(
        null,
        "steel@fake.com",
        null,
        null,
        null,
        null
    );
    this.runTest(
        new EndpointTest.Builder(put(ENDPOINT + "/9999"), status().isNotFound())
            .setRequestBody(putDTO)
            .setException(new ResourceNotFoundException("diaryUser"))
            .build()
    );
  }

  @Test
  public void testDiaryUser_DeleteNonExisting() throws Exception {
    this.runTest(
        new EndpointTest.Builder(
            delete(ENDPOINT + "/9999"),
            status().isNotFound()
        ).setException(new ResourceNotFoundException("diaryUser")).build()
    );
  }
}
