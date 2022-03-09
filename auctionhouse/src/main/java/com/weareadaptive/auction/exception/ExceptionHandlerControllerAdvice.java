package com.weareadaptive.auction.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

import com.weareadaptive.auction.exception.auction.AuctionClose;
import com.weareadaptive.auction.exception.auction.AuctionCreated;
import com.weareadaptive.auction.exception.auction.AuctionNotFound;
import com.weareadaptive.auction.exception.bid.BidException;
import com.weareadaptive.auction.exception.business.BusinessException;
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

  @ExceptionHandler(AuctionNotFound.class)
  public ResponseEntity<Object> handleNotFoundException(AuctionNotFound ex) {
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

  @ExceptionHandler(AuctionClose.class)
  public ResponseEntity<Object> handleAuctionAlreadyClosed(AuctionClose ex) {
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

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Object> handleAuctionAlreadyClosed(BusinessException ex) {
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

  @ExceptionHandler(AuctionCreated.class)
  public ResponseEntity<Object> handleAlreadyCreatedAuction(AuctionCreated ex) {
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

  @ExceptionHandler(BidException.class)
  public ResponseEntity<Object> handleAlreadyCreatedAuction(BidException ex) {
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


}
