package com.dasher.dashboard.adapter;

import com.dasher.dashboard.adapter.rest.exception.*;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@ControllerAdvice
public class ErrorHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

  private static ResponseEntity<Problem> handlerError(
      HttpStatus httpStatus, String typeException, Exception exception, Status status) {
    return ResponseEntity.status(httpStatus)
        .body(
            Problem.builder()
                .withTitle(typeException)
                .withDetail(exception.getMessage())
                .withStatus(status)
                .build());
  }

  @ExceptionHandler({ProjectNotFoundException.class, TaskNotFoundException.class})
  public ResponseEntity<Problem> entityNotFoundHandleException(final Exception exception) {
    LOGGER.error(exception.getMessage());
    return handlerError(HttpStatus.NOT_FOUND, "Not Found", exception, Status.NOT_FOUND);
  }

  @ExceptionHandler({
    BadRequestException.class,
    IllegalArgumentException.class,
    ProjectIllegalArgumentException.class,
    TaskIllegalArgumentException.class
  })
  public ResponseEntity<Problem> badRequestHandleException(final Exception exception) {
    LOGGER.error(exception.getMessage());
    return handlerError(HttpStatus.BAD_REQUEST, "Bad Request", exception, Status.BAD_REQUEST);
  }

  @ExceptionHandler({ProjectExistingException.class, TaskExistingException.class})
  public ResponseEntity<Problem> alreadyExistsHandleException(final Exception exception) {
    LOGGER.error(exception.getMessage());
    return handlerError(HttpStatus.CONFLICT, "Conflict", exception, Status.CONFLICT);
  }
}
