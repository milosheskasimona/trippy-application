package com.simonamilosheska.handlers;

import com.simonamilosheska.exceptions.DataBaseException;
import com.simonamilosheska.exceptions.EmptyListException;
import com.simonamilosheska.exceptions.city.NotExistingCityException;
import com.simonamilosheska.exceptions.venue.AlreadyExistVenueException;
import com.simonamilosheska.exceptions.venue.InvalidEnumInputException;
import com.simonamilosheska.exceptions.venue.NotExistingVenueByCreatorException;
import com.simonamilosheska.exceptions.venue.NotExistingVenueException;
import com.simonamilosheska.exceptions.review.NotExistingReviewByUserException;
import com.simonamilosheska.exceptions.review.NotExistingReviewException;
import com.simonamilosheska.exceptions.user.AlreadyExistUserException;
import com.simonamilosheska.exceptions.user.NotExistingEmailAddressException;
import com.simonamilosheska.exceptions.user.NotExistingUserException;
import com.simonamilosheska.exceptions.user.NotExistingUsernameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NotExistingUserException.class)
  public ResponseEntity<String> handleNotExistUserException(NotExistingUserException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotExistingEmailAddressException.class)
  public ResponseEntity<String> handleNotExistUserException(NotExistingEmailAddressException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotExistingUsernameException.class)
  public ResponseEntity<String> handleNotExistUserException(NotExistingUsernameException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AlreadyExistUserException.class)
  public ResponseEntity<String> handleNotExistUserException(AlreadyExistUserException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotExistingReviewByUserException.class)
  public ResponseEntity<String> handleNotExistUserException(NotExistingReviewByUserException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotExistingReviewException.class)
  public ResponseEntity<String> handleNotExistUserException(NotExistingReviewException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotExistingVenueException.class)
  public ResponseEntity<String> handleNotExistUserException(NotExistingVenueException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotExistingVenueByCreatorException.class)
  public ResponseEntity<String> handleNotExistUserException(NotExistingVenueByCreatorException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AlreadyExistVenueException.class)
  public ResponseEntity<String> handleNotExistUserException(AlreadyExistVenueException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidEnumInputException.class)
  public ResponseEntity<String> handleNotExistUserException(InvalidEnumInputException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotExistingCityException.class)
  public ResponseEntity<String> handleNotExistUserException(NotExistingCityException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataBaseException.class)
  public ResponseEntity<String> handleNotExistUserException(DataBaseException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmptyListException.class)
  public ResponseEntity<String> handleNotExistUserException(EmptyListException exception) {
    log.error("Caught exception: ", exception);
    String error = exception.getMessage();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException exception) {
    log.error("Caught exception: ", exception);
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleUnexpectedException(Exception exception) {
    log.error("Caught exception: ", exception);

    String error = "Something went wrong";

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
