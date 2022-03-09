package com.weareadaptive.auction.dto.response;

import com.weareadaptive.auction.model.auction.Auction;
import com.weareadaptive.auction.model.bid.Bid;
import java.time.Instant;
import java.util.List;

public class AuctionOwnerResponse extends AuctionResponse {
  public List<Bid> bids;
  public Auction.Status status;
  public Instant timeProvider;

  public AuctionOwnerResponse(
      int id,
      String owner,
      String symbol,
      double minPrice,
      int quantity,
      List<Bid> bids,
      Auction.Status status,
      Instant timeProvider) {
    super(id, owner, symbol, minPrice, quantity);
    this.bids = bids;
    this.status = status;
    this.timeProvider = timeProvider;
  }
}
