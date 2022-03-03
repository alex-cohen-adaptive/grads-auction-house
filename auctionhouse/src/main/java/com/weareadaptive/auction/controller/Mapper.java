package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.dto.response.AuctionResponse;
import com.weareadaptive.auction.dto.response.UserResponse;
import com.weareadaptive.auction.model.Auction;
import com.weareadaptive.auction.model.User;

public class Mapper {
    private Mapper() {
    }

    public static UserResponse map(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getOrganisation());
    }

    public static AuctionResponse map(Auction auction) {
        return new AuctionResponse(auction.getId(),auction.getOwner(),auction.getSymbol(),
                auction.getMinPrice(), auction.getQuantity());
    }

}
