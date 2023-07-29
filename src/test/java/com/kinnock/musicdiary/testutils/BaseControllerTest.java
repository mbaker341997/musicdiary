package com.kinnock.musicdiary.testutils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
  }
}
