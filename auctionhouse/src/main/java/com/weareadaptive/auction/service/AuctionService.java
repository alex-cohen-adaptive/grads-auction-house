package com.weareadaptive.auction.service;

import com.weareadaptive.auction.exception.auction.AuctionException;
import com.weareadaptive.auction.exception.auction.AuctionNotFound;
import com.weareadaptive.auction.exception.bid.BidException;
import com.weareadaptive.auction.exception.user.UserException;
import com.weareadaptive.auction.model.auction.Auction;
import com.weareadaptive.auction.model.user.User;
import com.weareadaptive.auction.model.bid.Bid;
import com.weareadaptive.auction.model.state.AuctionState;
import com.weareadaptive.auction.model.state.UserState;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

@Service
public class AuctionService {
  public static final String AUCTION_LOT_ENTITY = "AuctionLot";
  private final AuctionState auctionState;
  private final UserState userState;

  public AuctionService(AuctionState auctionState, UserState userState) {
    this.auctionState = auctionState;
    this.userState = userState;
  }

  private User getUser(String username) {
     var user = userState.getByUsername(username);
    if (user.isEmpty()) {
      throw new UserException("User Not Found!");
    }
    return user.get();
  }

  public Auction create(String username, String symbol, int quantity, double minPrice) {
    //todo check if null
    var user = getUser(username);
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
    var auctionLot = auctionState.get(id);
    if (auctionLot == null) {
      throw new AuctionNotFound();
    }
    return auctionLot;
  }


  public Stream<Auction> getAllAuctions() {
    return auctionState.stream();
  }


  public Bid bid(String username, int auctionId, double price, int quantity) {
    var auction = get(auctionId);
    var user = getUser(username);
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
    return null;

  }

  public String closeAuction(int id) {
    return null;
  }

  public String getAuctionSummary(int id) {
    return null;
  }

}
