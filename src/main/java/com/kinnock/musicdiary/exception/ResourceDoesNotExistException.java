package com.kinnock.musicdiary.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Request body includes a reference to a resource that does not exist. Created for POST requests
 * that reference related entities, but that entity does not exist. Example - a request to create
 * a song for an album, but the album with the provided id does not exist.
 * Distinguished from {@link ResourceNotFoundException} as that is for when we specifically want to
 * retrieve a resource that isn't found. Here, we are supplying a request that cannot be processed
 * because of a specific problem with the body.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceDoesNotExistException extends RuntimeException {

  public ResourceDoesNotExistException(String message) {
    super(message);
  }

  public static ResourceDoesNotExistException from(String resourceName, Long id) {
    return new ResourceDoesNotExistException(resourceName + " does not exist with id: " + id);
  }

  public static ResourceDoesNotExistException from(String resourceName, List<Long> ids) {
    return new ResourceDoesNotExistException(resourceName + " does not exist for these ids: " +
        String.join(",", ids.stream().map(String::valueOf).toList()));
  }
}
