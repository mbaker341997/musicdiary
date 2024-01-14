package com.kinnock.musicdiary.testutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Objects;

public class BaseControllerTest {
  @Autowired
  private MockMvc mockMvc;

  public void runTest(EndpointTest endpointTest) throws Exception {
    ResultActions result = mockMvc
        .perform(
            endpointTest.getRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.stringifyObject(endpointTest.getRequestBody()))
        )
        .andExpect(endpointTest.getResultMatcher());

    if (endpointTest.getResponseBody() != null) {
      result.andExpect(content().json(JsonUtils.stringifyObject(endpointTest.getResponseBody())));
    }

    if (endpointTest.getException() != null) {
      result.andExpect(r -> assertEquals(
              endpointTest.getException().getMessage(),
              Objects.requireNonNull(r.getResolvedException()).getMessage())
      );
      result.andExpect(r -> assertEquals(
              endpointTest.getException().getClass(),
              Objects.requireNonNull(r.getResolvedException()).getClass())
      );
    }
  }
}
