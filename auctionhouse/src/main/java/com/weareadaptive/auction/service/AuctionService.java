package com.weareadaptive.auction.service;

import static java.lang.String.format;

import com.weareadaptive.auction.exception.AuctionException;
import com.weareadaptive.auction.exception.BidException;
import com.weareadaptive.auction.exception.UserException;
import com.weareadaptive.auction.model.*;
import com.weareadaptive.auction.model.bid.Bid;
import com.weareadaptive.auction.model.state.AuctionState;
import com.weareadaptive.auction.model.state.UserState;
import com.weareadaptive.auction.security.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class AuctionService {
    public static final String AUCTION_LOT_ENTITY = "AuctionLot";
    private   final AuctionState auctionState;
    private final UserState userState;
    @Autowired
    private final AuthenticationProvider authenticationProvider;

    public AuctionService(
            AuctionState auctionState,
            UserState userState,
            AuthenticationProvider authenticationProvider) {

        this.auctionState = auctionState;
        this.userState = userState;
        this.authenticationProvider = authenticationProvider;
    }

    private User getUser(int userId) {
        var user = userState.get(userId);
        if (user == null) {
            throw new UserException("User Not Found!");
        }
        return user;
    }

    public Auction create(int id, String symbol, int quantity, double minPrice) {
        //todo check if null
        var user = getUser(id);
        var auctionLot = new Auction(
                auctionState.nextId(),
                user,
                symbol,
                quantity,
                minPrice
        );
        auctionState.add(auctionLot);
        return auctionLot;
    }

    //for testing purposes only
    public Auction create(User user, String symbol, int quantity, double minPrice) {
        var auctionLot = new Auction(
                auctionState.nextId(),
                user,
                symbol,
                quantity,
                minPrice
        );
        auctionState.add(auctionLot);

        return auctionLot;
    }


    public Auction get(int id) {
        var auctionLot = auctionState.get(id);
        if (auctionLot == null) {
            throw new AuctionException("Auction not found!");
        }
        return auctionLot;
    }


    public Stream<Auction> getAllAuctions() {
        return auctionState.stream();
    }


    public Bid bid(int auctionId, int userId, double price, int quantity) {
        var auction = get(auctionId);

        var user =  getUser(userId);
        if (user==auction.getOwner()) {
            throw new BidException("Owner cannot bid on his/her own auction!");
        }



        return auction.bid(
                getUser(userId),
                quantity,
                price
        );
    }


    public Stream<Bid> getAllBids(int id) {

    }

    public String closeAuction(int id) {

    }

    public String getAuctionSummary(int id) {
        return null;
    }

}
