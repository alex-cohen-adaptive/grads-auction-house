package com.weareadaptive.auction.dto.response;

public class AuctionResponse {
  public final int id;
  public final String owner;
  public final String symbol;
  public final double minPrice;
  public final int quantity;


  public AuctionResponse(int id, String owner, String symbol, double minPrice, int quantity) {
    this.id = id;
    this.owner = owner;
    this.symbol = symbol;
    this.minPrice = minPrice;
    this.quantity = quantity;
  }

}

