package com.kinnock.musicdiary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Used to indicate that a resource indicated in URL path was not found.
 * Primarily used for GETs and PUTs. If a POST on a resource with a non-existent constituent
 * resource is sent (example - creating an Album with an invalid DiaryUserId) then throw
 * {@link ResourceDoesNotExistException} instead.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }

  public static ResourceNotFoundException fromResourceName(String resourceName) {
    return new ResourceNotFoundException(resourceName + " not found");
  }
}
