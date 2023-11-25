package com.kinnock.musicdiary.concert;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("Integration")
@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerTests {
  private static final String ENDPOINT = "/api/v1/concerts";
}
