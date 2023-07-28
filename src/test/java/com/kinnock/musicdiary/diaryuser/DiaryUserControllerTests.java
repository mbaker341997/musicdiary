package com.kinnock.musicdiary.diaryuser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class DiaryUserControllerTests {
  private static DiaryUser usernameAlreadyTaken;
  private static DiaryUser emailAlreadyTaken;

  // TODO: create an ObjectMapper somewhere in some test utils package to serialize request/response bodies
  // TODO: set up some dummy database somewhere

  private static final String ENDPOINT = "/api/v1/user";

  @Autowired
  private MockMvc mvc;

  @Test
  public void testDiaryUser_HappyPath() throws Exception {
    // TODO: create a user
    mvc.perform(
          post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content("foo")
        )
        .andExpect(status().isOk());

    // TODO: read a user

    // TODO: read all users

    // TODO: update the user

    // TODO: read the update

    // TODO: delete the user
  }

  @Test
  public void testDiaryUser_UsernameAlreadyTaken() {
    // TODO: try to create one where username already taken

    // TODO: try to update one to a username that's already taken
  }

  @Test
  public void testDiaryUser_EmailAlreadyTaken() {
    // TODO: try to create one where email already taken

    // TODO: try to update one to an email that's already taken
  }
}
