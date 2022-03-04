package com.weareadaptive.auction.exception.auction;

public class AuctionNotFound extends RuntimeException {
  public AuctionNotFound() {
    super("Error! Auction Not Found!");
  }

  public AuctionNotFound(String message) {
    super(message);
  }
}
