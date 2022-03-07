package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.dto.response.AuctionOwnerResponse;
import com.weareadaptive.auction.dto.response.AuctionResponse;
import com.weareadaptive.auction.dto.response.BidResponse;
import com.weareadaptive.auction.dto.response.UserResponse;
import com.weareadaptive.auction.model.auction.Auction;
import com.weareadaptive.auction.model.bid.Bid;
import com.weareadaptive.auction.model.user.User;

public class Mapper {
  private Mapper() {
  }

  public static UserResponse map(User user) {
    return new UserResponse(
      user.getId(),
      user.getUsername(),
      user.getFirstName(),
      user.getLastName(),
      user.getOrganisation());
  }

  public static AuctionResponse map(Auction auction) {
    return new AuctionResponse(
      auction.getId(),
      auction.getOwner().getUsername(),
      auction.getSymbol(),
      auction.getMinPrice(),
      auction.getQuantity());
  }

  public static AuctionOwnerResponse mapOwner(Auction auction) {
    return new AuctionOwnerResponse(
      auction.getId(),
      auction.getOwner().getUsername(),
      auction.getSymbol(),
      auction.getMinPrice(),
      auction.getQuantity(),
      auction.getBids(),
      auction.getStatus(),
      auction.getTimeProvider());
  }

  public static BidResponse map(Bid bid) {
    return new BidResponse(
      bid.getUser().getUsername(),
      bid.getQuantity(),
      bid.getPrice()
    );
  }

}
