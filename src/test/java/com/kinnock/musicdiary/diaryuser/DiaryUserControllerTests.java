package com.kinnock.musicdiary.diaryuser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinnock.musicdiary.diaryuser.dto.DiaryUserDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPostDTO;
import com.kinnock.musicdiary.diaryuser.dto.DiaryUserPutDTO;
import com.kinnock.musicdiary.diaryuser.entity.DiaryUser;
import com.kinnock.musicdiary.testutils.JsonUtils;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class DiaryUserControllerTests {
  private static final String ENDPOINT = "/api/v1/user";

  @Autowired
  private MockMvc mvc;

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

    ResultActions postResultActions = mvc.perform(
          post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.stringifyObject(postDTO)));
    postResultActions.andExpect(status().isOk());
    
    DiaryUser diaryUserEntity = diaryUserRepository
        .findByUsername(postDTO.getUsername())
        .orElseThrow(() -> new AssertionFailedError("diary user not found in database"));
    DiaryUserDTO diaryUserDTO = new DiaryUserDTO(diaryUserEntity);

    postResultActions.andExpect(content().json(JsonUtils.stringifyObject(diaryUserDTO)));
    
    // GET read a user
    ResultActions getResultActions = mvc.perform(
        get(ENDPOINT + "/" + diaryUserEntity.getId()));
    getResultActions.andExpect(status().isOk());
    getResultActions.andExpect(content().json(JsonUtils.stringifyObject(diaryUserDTO)));

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
    ResultActions putResultActions = mvc.perform(
        put(ENDPOINT + "/" + diaryUserEntity.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.stringifyObject(putDTO)));
    putResultActions.andExpect(status().isOk());
    putResultActions.andExpect(content().json(JsonUtils.stringifyObject(updatedResponseDTO)));

    // GET the update
    ResultActions getAfterUpdateResultActions = mvc.perform(
        get(ENDPOINT + "/" + diaryUserEntity.getId()));
    getAfterUpdateResultActions.andExpect(status().isOk());
    getAfterUpdateResultActions.andExpect(content()
        .json(JsonUtils.stringifyObject(updatedResponseDTO))
    );

    // delete the user
    ResultActions deleteResultActions = mvc.perform(
        delete(ENDPOINT + "/" + diaryUserEntity.getId()));
    deleteResultActions.andExpect(status().isOk());
    deleteResultActions.andExpect(content()
        .json(JsonUtils.stringifyObject(updatedResponseDTO))
    );

    // GET the deleted and no result
    ResultActions getAfterDelete = mvc.perform(
        get(ENDPOINT + "/" + diaryUserEntity.getId()));
    getAfterDelete.andExpect(status().isNotFound());
  }

  @Test
  public void testDiaryUser_alreadyTaken() throws Exception {
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
    mvc.perform(
        post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.stringifyObject(postDTOUsernameTaken)))
        .andExpect(status().isBadRequest());

    // try to create one where email already taken
    DiaryUserPostDTO postDTOEmailTaken = new DiaryUserPostDTO(
        "DavidSteel1978",
        "steel@fake.com",
        "I am not leader of the Liberal Party",
        null,
        false,
        LocalDate.of(1913, Month.JULY, 23)
    );
    mvc.perform(
        post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.stringifyObject(postDTOEmailTaken)))
        .andExpect(status().isBadRequest());

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
    mvc.perform(
        put(ENDPOINT + "/" + jenkins.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.stringifyObject(putDTOUsernameTaken)))
        .andExpect(status().isBadRequest());

    // try to update to email already taken
    DiaryUserPutDTO putDTOEmailTaken = new DiaryUserPutDTO(
        null,
        "steel@fake.com",
        null,
        null,
        null,
        null
    );
    mvc.perform(
            put(ENDPOINT + "/" + jenkins.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.stringifyObject(putDTOEmailTaken)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testDiaryUser_UpdateNonExisting() throws Exception {
    DiaryUserPutDTO putDTOEmailTaken = new DiaryUserPutDTO(
        null,
        "steel@fake.com",
        null,
        null,
        null,
        null
    );
    mvc.perform(
            put(ENDPOINT + "/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.stringifyObject(putDTOEmailTaken)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testDiaryUser_DeleteNonExisting() throws Exception {
    ResultActions deleteResultActions = mvc.perform(
        delete(ENDPOINT + "/9999"));
    deleteResultActions.andExpect(status().isBadRequest());
  }
}
