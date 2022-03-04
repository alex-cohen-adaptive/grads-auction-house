package com.weareadaptive.auction.service;

import com.weareadaptive.auction.controller.IAuthentication;
import com.weareadaptive.auction.exception.auction.AuctionNotFound;
import com.weareadaptive.auction.exception.bid.BidException;
import com.weareadaptive.auction.exception.user.UserException;
import com.weareadaptive.auction.model.auction.Auction;
import com.weareadaptive.auction.model.auction.ClosingSummary;
import com.weareadaptive.auction.model.bid.Bid;
import com.weareadaptive.auction.model.state.AuctionState;
import com.weareadaptive.auction.model.state.UserState;
import com.weareadaptive.auction.model.user.User;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuctionService {
  @Autowired
  private IAuthentication authentication;

  public static final String AUCTION_LOT_ENTITY = "AuctionLot";
  private final AuctionState auctionState;
  private final UserState userState;

  public AuctionService(AuctionState auctionState, UserState userState) {
    this.auctionState = auctionState;
    this.userState = userState;
  }

  private User getUser() {
    var username = authentication.getAuthentication();
    var user = userState.getByUsername(username);
    if (user.isEmpty()) {
      throw new UserException("User Not Found!");
    }
    return user.get();
  }


  private Auction getAuction(int id) {
    var auction = auctionState.get(id);
    if (auction == null) {
      throw new AuctionNotFound();
    }
    return auction;
  }

  private boolean isOwner( int id) {
    return (getUser() == getAuction(id).getOwner());
  }

  public Auction create(String symbol, int quantity, double minPrice) {
    //todo check if null
    var user = getUser();
    var auctionLot = new Auction(auctionState.nextId(), user, symbol, quantity, minPrice);
    auctionState.add(auctionLot);
    return auctionLot;
  }

  public Auction create(User user, String symbol, int quantity, double minPrice) {
    //todo check if null
    var auctionLot = new Auction(auctionState.nextId(), user, symbol, quantity, minPrice);
    auctionState.add(auctionLot);
    return auctionLot;
  }


  public Auction get(int id) {
    return getAuction(id);
  }


  public Stream<Auction> getAllAuctions() {
    return auctionState.stream();
  }


  public Bid bid( int auctionId, double price, int quantity) {
    var auction = get(auctionId);
    var user = getUser();
    if (user == auction.getOwner()) {
      throw new BidException("Owner cannot bid on his/her own auction!");
    }
    return auction.bid(
      user,
      quantity,
      price
    );
  }


  public Stream<Bid> getAllBids(int id) {
    User user = getUser();
    if (!isOwner(id)) {
      throw new BidException("Only owner is allowed to get all bids");
    }
    return auctionState.get(id).getBids().stream();
  }

  public ClosingSummary closeAuction(int id) {
    if (!isOwner(id)) {
      throw new BidException("Only the owner can close his/her auction!");
    }
    var auction = getAuction(id);
    auction.close();
    return auction.getClosingSummary();
  }

  public ClosingSummary getAuctionSummary(int id) {
    var auction = getAuction(id);
    return auction.getClosingSummary();
  }

}
