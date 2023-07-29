package com.kinnock.musicdiary.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    OBJECT_MAPPER.registerModule(new JavaTimeModule());
  }

  public static String stringifyObject(Object obj) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(obj);
  }
}
