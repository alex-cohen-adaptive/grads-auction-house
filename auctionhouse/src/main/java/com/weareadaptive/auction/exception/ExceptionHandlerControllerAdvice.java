package com.weareadaptive.auction.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

import com.weareadaptive.auction.exception.http.BadRequestException;
import com.weareadaptive.auction.exception.http.NotAllowedException;
import com.weareadaptive.auction.exception.http.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object>
          handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

    var headers = new HttpHeaders();
    headers.setContentType(APPLICATION_PROBLEM_JSON);

    var invalidFields = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> new InvalidField(error.getField(), error.getDefaultMessage()))
        .toList();

    return new ResponseEntity<>(new BadRequestInvalidFieldsProblem(invalidFields), headers,
        BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
    var headers = new HttpHeaders();
    headers.setContentType(APPLICATION_PROBLEM_JSON);
    //ResponseEntity.notFound().build();
    return new ResponseEntity<>(
        new Problem(
            NOT_FOUND.value(),
            NOT_FOUND.name(),
            ex.getMessage()),
        headers,
        NOT_FOUND);
  }

  @ExceptionHandler(NotAllowedException.class)
  public ResponseEntity<Object> handleAuctionAlreadyClosed(NotAllowedException ex) {
    var headers = new HttpHeaders();
    headers.setContentType(APPLICATION_PROBLEM_JSON);
    return new ResponseEntity<>(
        new Problem(
            FORBIDDEN.value(),
            FORBIDDEN.name(),
            ex.getMessage()),
        headers,
        FORBIDDEN);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> handleAuctionAlreadyClosed(BadRequestException ex) {
    var headers = new HttpHeaders();
    headers.setContentType(APPLICATION_PROBLEM_JSON);
    return new ResponseEntity<>(
        new Problem(
            BAD_REQUEST.value(),
            BAD_REQUEST.name(),
            ex.getMessage()),
        headers,
        BAD_REQUEST);
  }




}
