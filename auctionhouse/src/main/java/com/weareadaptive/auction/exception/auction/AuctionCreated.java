package com.weareadaptive.auction.exception.auction;

public class AuctionCreated extends RuntimeException {
  public AuctionCreated() {
  }

  public AuctionCreated(String message) {
    super(message);
  }
}
