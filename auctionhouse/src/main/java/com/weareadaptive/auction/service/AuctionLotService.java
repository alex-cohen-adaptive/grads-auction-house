package com.weareadaptive.auction.service;

import static java.lang.String.format;

import com.weareadaptive.auction.controller.dto.AuctionResponse;
import com.weareadaptive.auction.controller.dto.BidResponse;
import com.weareadaptive.auction.controller.dto.CreateUserRequest;
import com.weareadaptive.auction.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.stream.Stream;

@Service
public class AuctionLotService {
    public static final String AUCTION_LOT_ENTITY = "AuctionLot";
    private final AuctionState auctionState;
    private final UserState userState;

    public AuctionLotService(AuctionState auctionState, UserState userState) {
        this.auctionState = auctionState;
        this.userState = userState;
    }


    public AuctionLot create(int id, String symbol, int quantity, double minPrice) {
        //todo check if null
        var user = userState.get(id);
        var auctionLot = new AuctionLot(
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
    public AuctionLot create(User user, String symbol, int quantity, double minPrice) {
        var auctionLot = new AuctionLot(
                auctionState.nextId(),
                user,
                symbol,
                quantity,
                minPrice
        );
        auctionState.add(auctionLot);

        return auctionLot;
    }


    public AuctionLot get(int id) {
        return null;
    }


    public Stream<AuctionLot> getAllAuctions(int id) {
        return null;
    }


    public Bid bid(int auctionId) {
        return null;
    }


    public Stream<Bid> getAllBids(int id) {
        return null;
    }

    public String getAuctionSummary(int id) {
        return null;
    }

}
