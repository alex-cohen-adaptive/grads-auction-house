package com.weareadaptive.auction.service;

import com.weareadaptive.auction.dto.request.CreateAuctionRequest;
import com.weareadaptive.auction.dto.request.CreateBidRequest;
import com.weareadaptive.auction.exception.BadRequestException;
import com.weareadaptive.auction.exception.NotFoundException;
import com.weareadaptive.auction.exception.bid.BidException;
import com.weareadaptive.auction.model.auction.Auction;
import com.weareadaptive.auction.model.auction.ClosingSummary;
import com.weareadaptive.auction.model.bid.Bid;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Stream;

import com.weareadaptive.auction.model.user.AuctionUser;
import com.weareadaptive.auction.repository.AuctionRepository;
import com.weareadaptive.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuctionService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  AuctionRepository auctionRepository;
  public static final String AUCTION_LOT_ENTITY = "AuctionLot";


/*  private final Auct
  private final AuctionState auctionState;

  public AuctionService(AuctionState auctionState) {
    this.auctionState = auctionState;
  }*/

  public void auctionExists(String symbol) {
    if (auctionRepository.existsBySymbol(symbol)) {
      throw new BadRequestException("Auction already exists!");
    }
  }

  //for testData class
  public Auction create(Principal principal, CreateAuctionRequest createAuction) {
    auctionExists(createAuction.symbol());
    var username = principal.getName();
    Auction auction = Auction.builder()
        .minPrice(createAuction.minPrice())
        .quantity(createAuction.quantity())
        .owner(username)
        .symbol(createAuction.symbol())
        .status(Auction.Status.OPENED.toString())
        .build();
    auctionRepository.save(auction);
    return auction;
  }

  public Auction create(String username, CreateAuctionRequest createAuction) {
    Auction auction = Auction.builder()
        .minPrice(createAuction.minPrice())
        .quantity(createAuction.quantity())
        .owner(username)
        .symbol(createAuction.symbol())
        .status("OPEN")
        .build();
    auctionRepository.save(auction);
    return auction;
  }



  private AuctionUser getUser(Principal principal) {
    var username = principal.getName();
    var user = userRepository.getByUsername(username);
    if (user.isEmpty()) {
      throw new NotFoundException(String.format("User %s not found!", username));
    }
    return user.get();
  }

  public Auction getAuction(int id) {
    var user = auctionRepository.getById(id);
    if (user.isEmpty()) {
      throw new NotFoundException("Auction not found!");
    }
    return user.get();
  }

  private boolean isOwner(int id, Principal principal) {
    return (getUser(principal).getUsername().equals(getAuction(id).getOwner()));
  }

  public Stream<Auction> getAllAuctions() {
    return auctionRepository.findAll().stream();
  }

  public Bid bid(Principal principal, int auctionId, CreateBidRequest createBidRequest) {
    var auction = getAuction(auctionId);
    var user = getUser(principal);
    if (isOwner(auctionId, principal)) {
      throw new BidException("Owner cannot bid on his/her own auction!");
    }
    return null;
    /*return auction.bid(
        user,
        quantity,
        price
    );*/
  }

  public Stream<Bid> getAllBids(int id, Principal principal) {
/*    if (!isOwner(id, principal)) {
      throw new BidException("Only owner is allowed to get all bids");
    }
    return auctionState.get(id).getBids().stream();*/
    return null;
  }

  public ClosingSummary closeAuction(int id, Principal principal) {
/*    if (!isOwner(id, principal)) {
      throw new BidException("Only the owner can close his/her auction!");
    }
    var auction = getAuction(id);
    auction.close();
    return auction.getClosingSummary();*/
    return null;
  }

  public ClosingSummary getAuctionSummary(int id) {
//    return getAuction(id).getClosingSummary();
    return null;
  }

}
