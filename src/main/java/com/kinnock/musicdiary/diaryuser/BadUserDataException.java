package com.kinnock.musicdiary.diaryuser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when attempting to create or update user data with invalid attributes.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadUserDataException extends RuntimeException {

  public BadUserDataException(String message) {
    super(message);
  }
}
